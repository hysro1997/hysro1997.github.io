package net.turanar.stellaris.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StellarisYamlReader extends Reader {
    private StringReader reader;

    // Fix Stellaris weird Yaml format
    public StellarisYamlReader(Path path) {
        StringBuffer retval = new StringBuffer();
        try {
            List<String> not_yaml_lines  = Files.readAllLines(path, Charset.forName("utf-8"));
            for(String line : not_yaml_lines) {
                int start = line.indexOf('"');
                if(start > 0) {
                    int end = line.lastIndexOf('"');
                    if(!(end == start+1)) {
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
            this.reader = new StringReader(retval.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return reader.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
