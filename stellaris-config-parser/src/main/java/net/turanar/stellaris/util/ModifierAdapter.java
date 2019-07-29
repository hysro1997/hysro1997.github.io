package net.turanar.stellaris.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.turanar.stellaris.domain.Modifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class ModifierAdapter implements JsonSerializer<Modifier> {
    @Override
    public JsonElement serialize(Modifier modifier, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(modifier.toString());
    }
}
