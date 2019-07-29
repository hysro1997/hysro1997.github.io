package net.turanar.stellaris.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.turanar.stellaris.domain.Modifier;
import net.turanar.stellaris.domain.WeightModifier;
import net.turanar.stellaris.util.ModifierAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {
    @Autowired
    GsonBuilder gsonBuilder;
    @Autowired
    ModifierAdapter modifierAdapter;

    @Bean
    public Gson gson() {
        return gsonBuilder
                .disableHtmlEscaping()
                .registerTypeAdapter(Modifier.class, modifierAdapter)
                .registerTypeAdapter(WeightModifier.class, modifierAdapter)
                .create();
    }
}
