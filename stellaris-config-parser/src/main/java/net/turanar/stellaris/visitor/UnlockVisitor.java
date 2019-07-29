package net.turanar.stellaris.visitor;

import net.turanar.stellaris.domain.GameObject;
import net.turanar.stellaris.domain.Technology;
import net.turanar.stellaris.antlr.StellarisParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static net.turanar.stellaris.Global.*;

import java.util.Map;

@Component
public class UnlockVisitor {
    @Autowired
    Map<String,Technology> technologies;

    public void visitFile(GameObject type, StellarisParser.FileContext ctx) {
        for(StellarisParser.PairContext pair : ctx.pair()) {
            this.visitPair(type, pair);
        }
    }

    public String clean(String prop) {
        String retval = prop.replaceAll("\\([^\\)]*\\)","");
        return retval.trim();
    }

    public Technology visitPair(GameObject type, StellarisParser.PairContext pair) {
        String key = pair.key();
        if(type == GameObject.STARBASE) {
            //System.out.println(key);
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
                tech = visitPair(type, props);
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
