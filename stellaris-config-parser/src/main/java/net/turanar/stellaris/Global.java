package net.turanar.stellaris;

import net.turanar.stellaris.antlr.StellarisParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Global {
    public static Map<String,String> GLOBAL_VARIABLES;
    public static Map<String,String> GLOBAL_STRINGS;
    public static Map<String, StellarisParser.PairContext> GLOBAL_TRIGGERS;
    public static String LS = "    •   ";

    public static void parse(String path, String filetype, Consumer<Path> consumer) throws IOException {
        PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:**." + filetype);
        Files.list(Paths.get(path)).filter(m::matches).forEach(consumer);
    }

    @Autowired
    public void init(Map<String,String> GLOBAL_VARIABLES, Map<String,String> GLOBAL_STRINGS,  Map<String, StellarisParser.PairContext> GLOBAL_TRIGGERS) {
        Global.GLOBAL_VARIABLES = GLOBAL_VARIABLES;
        Global.GLOBAL_STRINGS = GLOBAL_STRINGS;
        Global.GLOBAL_TRIGGERS = GLOBAL_TRIGGERS;
    }

    public static String i18n(String key) {
        String retval = GLOBAL_STRINGS.get(key.toLowerCase());
        if(retval == null) return key;
        if(retval.contains("$")) {
            retval = applyTemplate(retval);
        }
        return retval;
    }

    public static String i18n(StellarisParser.PairContext pair) {
        return i18n(gs(pair));
    }

    public static Boolean gbool(StellarisParser.PairContext pair) {
        return gs(pair).equals("yes");
    }

    public static String op(StellarisParser.PairContext p) {
        String operator = p.SPECIFIER().getText();
        if(">".equals(operator)) return "多于";
        if("<".equals(operator)) return "少于";
        if(">=".equals(operator)) return "多余等于";
        if("<=".equals(operator)) return "少于等于";
        return "等于";
    }

    public static String gs(StellarisParser.PairContext pair) {
        return gs(pair.value());
    }

    public static String gs(StellarisParser.ValueContext value) {
        if(value.BAREWORD() != null)
            return value.BAREWORD().getText();
        if(value.STRING() != null)
            return value.STRING().getText().replaceAll("\"","");
        if(value.VARIABLE() !=null)
            return GLOBAL_VARIABLES.get(value.VARIABLE().getText());
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

    public static String f(String f, String... objects) {
        return String.format(f, objects);
    }
}
