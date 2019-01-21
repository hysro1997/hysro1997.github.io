package net.turanar.stellaris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.turanar.stellaris.domain.Modifier;
import net.turanar.stellaris.domain.PopJob;
import net.turanar.stellaris.domain.Technology;
import net.turanar.stellaris.domain.WeightModifier;
import net.turanar.stellaris.parser.StellarisParser;
import net.turanar.stellaris.visitor.JobVisitor;
import net.turanar.stellaris.visitor.ModifierTypeAdapter;
import net.turanar.stellaris.visitor.TechnologyVisitor;
import net.turanar.stellaris.visitor.WeightModifierTypeAdapter;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static net.turanar.stellaris.Global.gs;
import static net.turanar.stellaris.Global.parser;

public class Jobs {
    public static String FOLDER;
    public static Set<PopJob> jobList = new TreeSet<PopJob>();
    public static Map<String,String> building_alias = new HashMap<>();

    public static void main(String[] args) throws Exception{
        FOLDER = args[0];
        Main.FOLDER = FOLDER;
        Global.init();
        JobVisitor visitor = new JobVisitor();

        Files.list(Paths.get(FOLDER + "/common/buildings"))
                .filter(path->path.toString().endsWith(".txt"))
                .forEach((path) -> {
                    try {
                        StellarisParser parser = parser(path);
                        if(parser == null) return;

                        List<StellarisParser.PairContext> pairs = parser.file().pair();
                        for(StellarisParser.PairContext pair : pairs) {
                            String key = pair.key();
                            String value = pair.key();
                            for(StellarisParser.PairContext p : pair.value().map().pair()) {
                                if("icon".equals(p.key())) value = gs(p);
                            }
                            building_alias.put(key,value);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        Files.list(Paths.get(FOLDER + "/common/pop_jobs"))
        .filter(path->path.toString().endsWith(".txt"))
        .forEach((path) -> {
            try {
                StellarisParser parser = parser(path);
                if(parser == null) return;

                List<StellarisParser.PairContext> pairs = parser.file().pair();
                for(StellarisParser.PairContext pair : pairs) {
                    PopJob job = visitor.visitPair(pair);
                    jobList.add(job);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(WeightModifier.class, new WeightModifierTypeAdapter());
        builder.registerTypeAdapter(Modifier.class, new ModifierTypeAdapter());
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        FileOutputStream fos = new FileOutputStream(FOLDER + "/jobs.json");
        String data = gson.toJson(jobList);
        fos.write(data.getBytes());
        fos.close();
    }
}
