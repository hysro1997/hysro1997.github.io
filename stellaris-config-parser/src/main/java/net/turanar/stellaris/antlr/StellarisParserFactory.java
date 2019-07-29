package net.turanar.stellaris.antlr;

import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class StellarisParserFactory {
    public StellarisParser getParser(Path path) {
        StellarisParser retval = new StellarisParser(new StellarisTokenStream(path));
        return retval;
    }
}
