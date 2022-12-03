package net.turanar.stellaris.domain;

import net.turanar.stellaris.parser.StellarisParser.*;

import java.util.ArrayList;
import java.util.List;

public class WeightModifier extends Modifier {
    public Float factor = 1.0f;
    public Integer add  = 0;

    @Override
    public String toString() {
        String format = "(×%s)";
        if(this.add > 0) format = "(+%s)";
        if(type != null) format += " %s";
        String s_factor = "<b style='color:lime'>" + factor + "</b>";
        if(factor < 1.0f) s_factor = "<b style='color:red'>" + factor + "</b>";
        return String.format(format, s_factor, type != null ? type.parse(pair).replaceAll("\\n","<br/>") : "");
    }
}
