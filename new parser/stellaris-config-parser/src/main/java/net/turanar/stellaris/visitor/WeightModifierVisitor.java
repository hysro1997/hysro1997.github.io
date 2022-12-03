package net.turanar.stellaris.visitor;

import net.turanar.stellaris.Global;
import net.turanar.stellaris.domain.Modifier;
import static net.turanar.stellaris.domain.ModifierType.*;
import net.turanar.stellaris.domain.Technology;
import net.turanar.stellaris.domain.WeightModifier;
import net.turanar.stellaris.parser.StellarisParser.MapContext;
import net.turanar.stellaris.parser.StellarisParser.PairContext;

import java.util.ArrayList;
import java.util.List;

import static net.turanar.stellaris.Global.gs;

public class WeightModifierVisitor {
    Technology tech;

    public WeightModifierVisitor(Technology tech) {
        this.tech = tech;
    }

    public List<WeightModifier> visitMap(MapContext map) {
        List<WeightModifier> retval = new ArrayList<>();
        for(PairContext pair : map.pair()) {
            WeightModifier modifier = visitModifier(pair);
            if(modifier != null) retval.add(modifier);
        }
        return retval;
    }

    public List<Modifier> visitPotential(MapContext map) {
        List<Modifier> retval = new ArrayList<>();
        for(PairContext pair : map.pair()) {
            Modifier modifier = visitCondition(pair);
            if(modifier != null) retval.add(modifier);
        }
        return retval;
    }

    public WeightModifier visitModifier(PairContext modifier) {
        WeightModifier retval = null;

        if("modifier".equals(modifier.key())) {
            retval = new WeightModifier();

            for(PairContext pair : modifier.value().map().pair()) {
                if (pair.key().equals("factor")) {
                    retval.factor = Float.valueOf(gs(pair.value()));
                } else if(pair.key().equals("add")) {
                    retval.add = Integer.valueOf(gs(pair.value()));
                } else {
                    Modifier m = visitCondition(pair);
                    retval.type = m.type;
                    retval.pair = m.pair;
                }
            }
        } else if ("factor".equals(modifier.key())) {
            String value = gs(modifier);
            if(value.startsWith("@")) value = Global.variable(value);
            Float f = Float.valueOf(value);
            tech.base_weight = tech.base_weight * f;
        }

        return retval;
    }

}
