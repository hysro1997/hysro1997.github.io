package net.turanar.stellaris;

import net.turanar.stellaris.parser.StellarisLexer;
import net.turanar.stellaris.parser.StellarisParser;
import net.turanar.stellaris.parser.ThrowingErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import net.turanar.stellaris.parser.StellarisParser.*;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Global {
    public static Map<String,String> GLOBAL_STRINGS = new HashMap<>();
    public static Map<String,String> GLOBAL_VARIABLES = new HashMap<>();
    public static Map<String,PairContext> SCRIPTED_TRIGGERS = new HashMap<>();

    public static String LS = "    •   ";

    public static String f(String f, String... objects) {
        return String.format(f, objects);
    }

    public static String i18n(String key) {
        String retval = GLOBAL_STRINGS.get(key.toLowerCase());
        if(retval == null) return key;
        if(retval.contains("$")) {
            retval = applyTemplate(retval);
        }
        return retval;
    }

    public static String op(PairContext p) {
        String operator = p.SPECIFIER().getText();
        if(">".equals(operator)) return "多于";
        if("<".equals(operator)) return "少于";
        if(">=".equals(operator)) return "多于等于";
        if("<=".equals(operator)) return "少于等于";
        return "等于";
    }

    public static String gs(PairContext pair) {
        return gs(pair.value());
    }

    public static String gs(ValueContext value) {
        if(value.BAREWORD() != null)
            return value.BAREWORD().getText();
        if(value.STRING() != null)
            return value.STRING().getText().replaceAll("\"","");
        if(value.VARIABLE() !=null)
            return i18n(value.VARIABLE().getText());
        if(value.BOOLEAN() != null)
            return value.BOOLEAN().getText();
        if(value.NUMBER() != null)
            return value.NUMBER().getText();
        return null;
    }

    public static String variable(String key) {
        return GLOBAL_VARIABLES.get(key);
    }

    public static String applyTemplate(String retval) {
        Pattern p = Pattern.compile("\\$([a-zA-z0-9_]+)\\$");

        int i = 0;
        while(retval.contains("$") && i < 2) {
            Matcher m2 = p.matcher(retval);
            if(m2.find()) {
                retval = m2.replaceFirst(i18n(m2.group(1)));
                m2.reset();
            }
            i++;
        }
        return retval;
    }

    private static void parseTriggers(Path path) {
        try {
            StellarisParser parser = parser(path);
            if(parser == null) return;
            for(ParseTree child : parser.file().children) {
                if(child instanceof PairContext) {
                    PairContext pair = (PairContext)child;
                    SCRIPTED_TRIGGERS.put(pair.key(), pair);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseVariables(Path path) {
        try {
            StellarisParser parser = parser(path);
            if(parser == null) return;
            for(ParseTree child : parser.file().children) {
                if(child instanceof VarContext) {
                    VarContext var = (VarContext)child;
                    GLOBAL_VARIABLES.put(var.VARIABLE().getText(), var.NUMBER().getText());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() throws IOException {
        Files.list(Paths.get(Main.FOLDER + "/localisation/simp_chinese"))
        .filter(path->path.toString().endsWith(".yml"))
        .forEach(path -> {
            Yaml yaml = new Yaml();
            try {
                Iterable<Object> data = yaml.loadAll(getLocaleContent(path));
                Map<String,Map<Object,Object>> map = (Map<String,Map<Object,Object>>)data.iterator().next();
                Map<Object,Object> values = map.get("l_simp_chinese");
                for(Map.Entry<Object,Object> entry : values.entrySet()) {
                    if(entry.getKey() != null && entry.getValue() != null) {
                        GLOBAL_STRINGS.put(entry.getKey().toString().toLowerCase(), entry.getValue().toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Files.list(Paths.get(Main.FOLDER + "/common/technology"))
        .filter(path->path.toString().endsWith(".txt"))
        .forEach((path) -> {
            parseVariables(path);
        });

        // Parse scripted variables
        Files.list(Paths.get(Main.FOLDER + "/common/scripted_variables"))
        .filter(path->path.toString().endsWith(".txt"))
        .forEach((path) -> {
            parseVariables(path);
        });

        Files.list(Paths.get(Main.FOLDER + "/common/scripted_triggers"))
        .filter(path->path.toString().endsWith(".txt"))
        .forEach((path) -> {
            parseTriggers(path);
        });

        // Parse variable in technology files
    }

    private static String getLocaleContent(Path path) throws IOException {
        StringBuffer retval = new StringBuffer();
        List<String> not_yaml_lines  = Files.readAllLines(path, Charset.forName("utf-8"));
        for(String line : not_yaml_lines) {
            int start = line.indexOf('"');
            if(start > 0) {
                int end = line.lastIndexOf('"');
                if(!(end <= start+1)) {
                    String fixed = line.substring(start + 1,end);
                    fixed = fixed.replaceAll("\"", "\\\\\"");
                    line = line.substring(0, start + 1) + fixed + line.substring(end);
                }
            }

            line = line.replace("\uFEFF", "");
            line = line.replaceAll("£\\w+  |§[A-Z!]","");
            line = line.replaceAll("(?<=\\w):\\d+ ?(?=\")", ": ");
            line = line.replaceAll("^[ \\t]+"," ");
            retval.append(line).append(System.lineSeparator());
        }
        return retval.toString();
    }

    private static String getContent(Path path) throws IOException {
        List<String> output = new ArrayList<>();
        List<String> lines = Files.readAllLines(path);
        for(String line : lines) {
            line = line.replaceAll("\"(north|east|west|south|mid|bow|stern|core|ship)\"","$1");
            line = line.replaceAll("\"(\\d)\"","fix_$1");
            line = line.replaceAll("event_target:","");

            if(!line.startsWith("#")) output.add(line);
        }

        String content = String.join("\n", output);
        if(content.trim().isEmpty()) return null;
        return content;
    }

    public static StellarisParser parser(Path path) throws IOException {
        String content = getContent(path);
        if(content == null) return null;

        CharStream stream = CharStreams.fromString(content);
        StellarisLexer lex = new StellarisLexer(stream);
        lex.removeErrorListeners();
        lex.addErrorListener(ThrowingErrorListener.INSTANCE);

        TokenStream tokenStream = new CommonTokenStream(lex);
        StellarisParser parser = new StellarisParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        return parser;
    }
}
