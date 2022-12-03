package net.turanar.stellaris.visitor;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.turanar.stellaris.domain.Modifier;
import net.turanar.stellaris.domain.WeightModifier;

import java.lang.reflect.Type;

public class ModifierTypeAdapter implements JsonSerializer<Modifier> {

    @Override
    public JsonElement serialize(Modifier weightModifier, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(weightModifier.toString());
    }
}
