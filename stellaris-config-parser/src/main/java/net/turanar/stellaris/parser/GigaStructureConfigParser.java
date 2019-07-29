package net.turanar.stellaris.parser;

import net.turanar.stellaris.antlr.StellarisParserFactory;
import net.turanar.stellaris.domain.Technology;
import net.turanar.stellaris.util.StellarisYamlReader;
import net.turanar.stellaris.visitor.TechnologyVisitor;
import net.turanar.stellaris.visitor.UnlockVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.turanar.stellaris.Global.parse;

@Component
public class GigaStructureConfigParser extends AbstractConfigParser {
    @Autowired
    TechnologyVisitor techVisitor;
    @Autowired
    UnlockVisitor unlockVisitor;
    @Autowired
    StellarisParserFactory factory;
    @Autowired
    Map<String,String> GLOBAL_STRINGS;

    List<Technology> moddedtech = new ArrayList<>();

    public void parseTechnolgies(String folder) throws IOException {
        parse(folder + "/common/technology","txt", f -> {
            factory.getParser(f).file().pair().forEach(p -> {
                Technology t = techVisitor.visitPair(p);
                t.source = "gigastructure";
                technologies.put(t.key, t);
                moddedtech.add(t);
            });
        });
    }

    public void parseLocalisation(String folder) throws IOException {
        parse(folder + "/localisation", "yml", path -> {
            Yaml yaml = new Yaml();
            if(!path.getFileName().toString().equals("gigaengineering_l_english.yml")) return;
            Iterable<Object> data = yaml.loadAll(new StellarisYamlReader(path));
            Map<String,Map<Object,Object>> map = (Map<String,Map<Object,Object>>)data.iterator().next();
            if (map.get("l_english") == null) return;
            map.get("l_english").forEach((k, v) -> {
                GLOBAL_STRINGS.put(k.toString().toLowerCase(), v.toString());
            });
        });
    }

    @Override
    public void read(String folder) throws IOException {
        parseLocalisation(folder);
        parseTechnolgies(folder);
        Technology giga = new Technology();
        giga.name = "gigastructure"; giga.key = "gigastructure";
        giga.children.addAll(moddedtech);
        Technology rootG = new Technology();
        rootG.tier = 0;
        rootG.children.add(giga);
        writeJson("gigastructure.json", rootG);
    }
}
