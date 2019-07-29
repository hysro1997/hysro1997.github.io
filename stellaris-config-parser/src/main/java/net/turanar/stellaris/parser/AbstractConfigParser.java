package net.turanar.stellaris.parser;

import com.google.gson.Gson;
import net.turanar.stellaris.domain.Modifier;
import net.turanar.stellaris.domain.ModifierType;
import net.turanar.stellaris.domain.Technology;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static net.turanar.stellaris.Global.gs;

public abstract class AbstractConfigParser {
    @Autowired
    protected Map<String, Technology> technologies;
    @Autowired
    Gson gson;

    public void writeJson(String filename, Technology... techs) throws IOException {
        FileOutputStream fos = new FileOutputStream("output/" + filename);
        String data;
        if(techs.length == 1) { data = gson.toJson(techs[0]); }
        else {
            Technology root = new Technology();
            Arrays.stream(techs).forEach(t -> root.children.addAll(t.children));
            data = gson.toJson(root);
        }
        fos.write(data.getBytes());
        fos.close();
    }

    public void prepare(Map<String,Technology> technologies) {
        technologies.values().forEach(tech -> {
            if(tech.tier == null) tech.tier = 0;
            if(tech.cost == null) tech.cost = 0;
            if(tech.is_start_tech) tech.prerequisites.clear();

            Iterator<String> iter = tech.prerequisites.iterator();
            while(iter.hasNext()) {
                String preq = iter.next();
                Technology reqTech = technologies.get(preq);
                if(reqTech.is_start_tech && reqTech.area != tech.area) iter.remove();
            }

            for(String preq : tech.prerequisites) {
                Technology reqTech = technologies.get(preq);
                HashMap<String,String> item = new HashMap<>();
                item.put("key", reqTech.key);
                item.put("name", reqTech.name);
                tech.prerequisites_names.add(item);
            }

            tech.base_weight = tech.base_weight*tech.base_factor;

            // Flag Event Techs
            if(tech.base_weight == 0 && tech.prerequisites.size() < 1 && !tech.is_start_tech) tech.is_event = true;
            if(tech.base_weight == 0 && !tech.key.equals("tech_colossus") && !tech.key.equals("tech_mine_living_metal") && !tech.is_start_tech) tech.is_event = true;
            if(tech.base_weight > 0 && tech.weight_modifiers.size() > 0 && tech.weight_modifiers.get(0).type == ModifierType.always && tech.weight_modifiers.get(0).factor == 0.0f) tech.is_event = true;

            // Re-order prerequisite so the most costly is first AND must be the same AREA
            tech.prerequisites.sort((o1, o2) -> {
                Technology parent1 = technologies.get(o1);
                Technology parent2 = technologies.get(o2);

                // Same AREA - will compare key - are they similar ? ie. tech_energy_lance_1 vs tech_energy_lance_2
                String key1 = parent1.key.replaceAll("\\d","");
                String key2 = tech.key.replaceAll("\\d","");

                if(key1.equals(key2)) return -1;

                // Same AREA - will compare Costs
                if(parent1.area.equals(tech.area) && parent2.area.equals(tech.area)) {
                    return parent1.cost.compareTo(parent2.cost);
                }
                // Not same AREA - Will prioritize the one the same as child tech
                if(parent1.area.equals(tech.area) && !parent2.area.equals(tech.area)) return -1;
                if(!parent1.area.equals(tech.area) && parent2.area.equals(tech.area)) return 1;

                return 0;
            });

            for(Modifier m : tech.potential) {
                if(m.type.equals(ModifierType.is_gestalt)) {
                    if(gs(m.pair).equals("yes")) tech.is_gestalt = true;
                    else tech.is_gestalt = false;
                }
                if(m.type.equals(ModifierType.is_megacorp)) {
                    if(gs(m.pair).equals("yes")) tech.is_megacorp = true;
                    else tech.is_megacorp = false;
                }
                if(m.type.equals(ModifierType.is_machine_empire)) {
                    if(gs(m.pair).equals("yes")) tech.is_machine_empire = true;
                    else tech.is_machine_empire = false;
                }
                if(m.type.equals(ModifierType.is_hive_empire)) {
                    if(gs(m.pair).equals("yes")) tech.is_hive_empire = true;
                    else tech.is_hive_empire = false;
                }
                String str = m.toString();
                if(str.contains("Machine Intelligence Authority")) {
                    if(str.contains(" NOT have Machine Intelligence Authority")) {
                        tech.is_machine_empire = false;
                    } else {
                        tech.is_machine_empire = true;
                    }
                    if(str.contains("Has Government Civic: Driven Assimilator")) {
                        tech.is_drive_assimilator = true;
                    }
                    if(str.contains("Has Government Civic: Rogue Servitor")) {
                        tech.is_rogue_servitor = true;
                    }
                } else if (str.contains("Gestalt Consciousness Ethic")) {
                    if(str.contains(" NOT ")) {
                        tech.is_gestalt = false;
                    } else {
                        tech.is_gestalt = true;
                    }
                } else if (str.contains("Hive Mind Authority")) {
                    if(str.contains(" NOT ")) {
                        tech.is_hive_empire = false;
                    } else {
                        tech.is_hive_empire = true;
                    }
                }
            }
        });

        technologies.values().stream().filter(tech -> tech.prerequisites.size()> 0).forEach(tech -> {
            if(tech.is_event) return;
            String parent = tech.prerequisites.get(0);
            technologies.get(parent).children.add(tech);
        });
    }

    public abstract void read(String folder) throws IOException;
}
