package net.turanar.stellaris;

import net.turanar.stellaris.domain.Technology;
import net.turanar.stellaris.util.StellarisYamlReader;
import net.turanar.stellaris.antlr.StellarisParser;
import net.turanar.stellaris.antlr.StellarisParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static net.turanar.stellaris.Global.*;

@Configuration
public class Config {
    @Autowired
    StellarisParserFactory factory;

    static String FOLDER = "E:\\Program Files (x86)\\Steam\\steamapps\\common\\Stellaris";

    @Bean("GLOBAL_VARIABLES")
    public Map<String,String> variables() throws IOException {
        HashMap<String,String> retval = new HashMap<>();

        parse(FOLDER + "/common/scripted_variables", "txt", p -> {
            factory.getParser(p).file().var().forEach(v -> retval.put(v.VARIABLE().getText(), v.NUMBER().getText()));
        });

        parse(FOLDER + "/common/technology", "txt", p -> {
            factory.getParser(p).file().var().forEach(v -> retval.put(v.VARIABLE().getText(), v.NUMBER().getText()));
        });

        return retval;
    }

    @Bean("GLOBAL_STRINGS")
    public Map<String,String> localisation() throws IOException {
        Map<String,String> retval = new HashMap<>();

        parse(FOLDER + "/localisation/simp_chinese", "yml", path -> {
            Yaml yaml = new Yaml();
            Iterable<Object> data = yaml.loadAll(new StellarisYamlReader(path));
            for (;data.iterator().hasNext();){
            Map<String,Map<Object,Object>> map = (Map<String,Map<Object,Object>>)data.iterator().next();
            map.get("l_simp_chinese").forEach((k, v) -> {
                retval.put(k.toString().toLowerCase(), v.toString());
            });
            }
        });

        return retval;
    }

    @Bean("SCRIPTED_TRIGGERS")
    public Map<String, StellarisParser.PairContext> scriptedTriggers() throws IOException {
        Map<String, StellarisParser.PairContext> retval = new HashMap<>();

        parse(FOLDER + "/common/scripted_triggers", "txt", path -> {
            factory.getParser(path).file().pair().forEach(pair -> retval.put(pair.key(), pair));
        });

        return retval;
    }

    @Bean("technologies")
    public Map<String, Technology> technologies() {
        return new HashMap<>();
    }

}
