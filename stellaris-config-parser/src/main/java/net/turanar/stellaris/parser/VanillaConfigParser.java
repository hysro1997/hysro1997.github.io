package net.turanar.stellaris.parser;

import net.turanar.stellaris.antlr.StellarisParserFactory;
import net.turanar.stellaris.domain.Area;
import net.turanar.stellaris.domain.GameObject;
import net.turanar.stellaris.domain.Technology;
import net.turanar.stellaris.visitor.TechnologyVisitor;
import net.turanar.stellaris.visitor.UnlockVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static net.turanar.stellaris.Global.parse;

@Component
public class VanillaConfigParser extends AbstractConfigParser {
    @Autowired
    TechnologyVisitor techVisitor;
    @Autowired
    UnlockVisitor unlockVisitor;
    @Autowired
    StellarisParserFactory factory;

    ArrayList<Technology> anomalies = new ArrayList<>();

    public void parseTechnolgies(String folder) throws IOException {
        parse(folder + "/common/technology","txt", f -> {
            factory.getParser(f).file().pair().forEach(p -> {
                Technology t = techVisitor.visitPair(p);
                technologies.put(t.key, t);
            });
        });
    }

    public void parseGamesObjets(String folder) throws IOException {
        Arrays.stream(GameObject.values()).forEach(type -> {
            try {
                parse(folder + "/" + type.folder, "txt", path -> {
                    if (path.getFileName().toString().startsWith("README")) return;
                    if (!path.getFileName().toString().endsWith(type.filter)) return;
                    unlockVisitor.visitFile(type, factory.getParser(path).file());
                });
            } catch (IOException e) {e.printStackTrace();}
        });
    }

    public Technology areaTech(Area area) {
        Technology t = new Technology();
        t.tier = 0; t.name = area.name(); t.area = area;
        return t;
    }

    public Technology gather(Area area) {
        Technology retval = areaTech(area);
        retval.children.addAll(
            technologies.values().stream()
                    .filter(t -> t.area.equals(area) && t.prerequisites.size() < 1 && !t.is_event)
                    .collect(Collectors.toList())
        );
        return retval;
    }

    public void anomalies() throws IOException {
        technologies.values().stream().filter(t -> t.is_event).forEach(t -> {
            anomalies.add(t);
            if(t.children.size() > 0) t.children.forEach(c -> {c.is_event = true; anomalies.add(c);});
        });

        anomalies.sort((o1, o2) -> {
            if(o1 == o2) return 0;
            if(o1.equals(o2)) return 0;
            return o1.key.compareTo(o2.key);
        });

        FileOutputStream fos = new FileOutputStream("output/anomalies.json");
        fos.write(gson.toJson(anomalies).getBytes());
        fos.close();
    }

    @Override
    public void read(String folder) throws IOException {
        parseTechnolgies(folder);
        parseGamesObjets(folder);
        prepare(technologies);
        anomalies();

        Technology rootP = new Technology(gather(Area.physics));
        writeJson("physics.json", rootP);
        Technology rootS = new Technology(gather(Area.society));
        writeJson("society.json", rootS);
        Technology rootE = new Technology(gather(Area.engineering));
        writeJson("engineering.json", rootE);
    }
}
