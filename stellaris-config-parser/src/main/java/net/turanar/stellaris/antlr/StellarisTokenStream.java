package net.turanar.stellaris.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StellarisTokenStream extends CommonTokenStream {
    private static String fixContent(Path path) {
        try {
            List<String> output = new ArrayList<>();
            List<String> lines = Files.readAllLines(path);
            for(String line : lines) {
                line = line.replaceAll("\"(north|east|west|south|mid|bow|stern|core|ship|bot_1|bot_2|bot_3)\"","$1");
                line = line.replaceAll("\"(\\d)\"","fix_$1");
                line = line.replaceAll("hidden:","");
                line = line.replaceAll("\\$([^\\$]*\\|[^\\$]*|[^\\$]*)\\$","ARG1");
                line = line.replaceAll("event_target:","");

                if(!line.startsWith("#")) output.add(line);
            }

            String content = String.join("\n", output);
            if(content.trim().isEmpty()) return "";
            return content;
        } catch (IOException e) {
            return "";
        }
    }

    private static TokenSource createTokenSource(Path path) {
        String content = fixContent(path);
        CharStream stream = CharStreams.fromString(content);
        StellarisLexer lex = new StellarisLexer(stream);
        lex.removeErrorListeners();
        lex.addErrorListener(ThrowingErrorListener.INSTANCE);
        return lex;
    }

    public StellarisTokenStream(Path path) {
        super(createTokenSource(path));
    }
}
