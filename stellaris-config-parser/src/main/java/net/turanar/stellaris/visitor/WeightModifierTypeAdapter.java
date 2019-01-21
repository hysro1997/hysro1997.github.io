package net.turanar.stellaris.visitor;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.turanar.stellaris.domain.Modifier;
import net.turanar.stellaris.domain.WeightModifier;

import java.lang.reflect.Type;

public class WeightModifierTypeAdapter implements JsonSerializer<WeightModifier> {

    @Override
    public JsonElement serialize(WeightModifier weightModifier, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(weightModifier.toString());
    }
}
