package net.turanar.stellaris.visitor;

import net.turanar.stellaris.domain.GameObject;
import net.turanar.stellaris.domain.Technology;
import net.turanar.stellaris.parser.StellarisBaseVisitor;
import net.turanar.stellaris.parser.StellarisParser;
import static net.turanar.stellaris.Global.*;

import java.util.Map;

public class UnlockVisitor extends StellarisBaseVisitor<Technology> {
    Map<String,Technology> technologies;

    GameObject type;
    public UnlockVisitor(GameObject type, Map<String,Technology> technologyMap) {
        this.technologies = technologyMap;
        this.type = type;
    }

    @Override
    public Technology visitFile(StellarisParser.FileContext ctx) {
        for(StellarisParser.PairContext pair : ctx.pair()) {
            this.visitPair(pair);
        }
        return null;
    }

    public String clean(String prop) {
        String retval = prop.replaceAll("\\([^\\)]*\\)","");
        return retval.trim();
    }

    @Override
    public Technology visitPair(StellarisParser.PairContext pair) {
        String key = pair.key();
        if(type == GameObject.STARBASE) {
            System.out.println(key);
        }
        Technology tech = null;
        if(pair.value().map() == null) return null;

        for(StellarisParser.PairContext props : pair.value().map().pair()) {
            if(props.key().equals("prerequisites")) {
                if(props.value().array() == null) continue;
                for(StellarisParser.ValueContext ctx : props.value().array().value()) {
                    tech = technologies.get(gs(ctx));
                }
            } else if (props.key().equals("key") || props.key().equals("name")) {
                key = gs(props.value());
            } else if (props.key().equals("show_in_tech")) {
                tech = technologies.get(gs(props.value()));
            } else if (props.key().equals("option") && type == GameObject.POLICY) {
                key = null;
                tech = visitPair(props);
            }
        }

        if(key == null) return tech;

        if(i18n(key).equals(key)) {
            key = i18n(type.locale_prefix + key);
        } else {
            key = i18n(key);
        }

        if(tech != null) tech.feature_unlocks.add(clean("<b>" + type.label + "</b>: " + key));

        return tech;
    }
}
