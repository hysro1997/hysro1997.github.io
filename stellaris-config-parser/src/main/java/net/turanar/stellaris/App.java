package net.turanar.stellaris;

import com.google.gson.Gson;
import net.turanar.stellaris.domain.*;
import net.turanar.stellaris.antlr.StellarisParserFactory;
import net.turanar.stellaris.parser.GigaStructureConfigParser;
import net.turanar.stellaris.parser.VanillaConfigParser;
import net.turanar.stellaris.visitor.TechnologyVisitor;
import net.turanar.stellaris.visitor.UnlockVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static net.turanar.stellaris.Global.gs;
import static net.turanar.stellaris.Global.parse;

@SpringBootApplication(scanBasePackages = "net.turanar.stellaris")
@PropertySource("classpath:/application.properties")
public class App {
    @Autowired
    VanillaConfigParser vanilla;
    @Autowired
    GigaStructureConfigParser gigaStructure;
    static String FOLDER = "E:\\Program Files (x86)\\Steam\\steamapps\\common\\Stellaris";

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            vanilla.read(FOLDER);
        };
    }
}
