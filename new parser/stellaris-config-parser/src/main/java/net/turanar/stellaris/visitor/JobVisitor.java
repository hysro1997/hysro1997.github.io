package net.turanar.stellaris.visitor;

import net.turanar.stellaris.Jobs;
import net.turanar.stellaris.domain.PopCategory;
import net.turanar.stellaris.domain.PopJob;
import net.turanar.stellaris.parser.StellarisBaseVisitor;
import net.turanar.stellaris.parser.StellarisParser;
import net.turanar.stellaris.parser.StellarisVisitor;
import org.apache.commons.lang3.math.NumberUtils;

import static net.turanar.stellaris.Global.*;

public class JobVisitor extends StellarisBaseVisitor<PopJob> {
    @Override
    public PopJob visitPair(StellarisParser.PairContext ctx) {
        PopJob retval = new PopJob();
        retval.effect = "";
        retval.name = i18n("job_" + ctx.key());
        retval.description = i18n("job_" + ctx.key() + "_desc");
        retval.key = ctx.key();
        retval.icon = ctx.key();

        StellarisParser.MapContext map = ctx.value().map();
        for(StellarisParser.PairContext pair : map.pair()) {
            switch(pair.key()) {
                case "icon": retval.icon = gs(pair); break;
                case "category": retval.category = PopCategory.valueOf(gs(pair)); break;
                case "building_icon": {
                    retval.building = gs(pair);
                    if (Jobs.building_alias.containsKey(gs(pair))) {
                        retval.building = Jobs.building_alias.get(gs(pair));
                    }
                    break;
                }
                case "resources": {
                    for (StellarisParser.PairContext item : pair.value().map().pair()) {
                        if ("produces".equals(item.key())) {
                            if (item.value().map().pair().get(0).key().equals("trigger")) continue;
                            for (StellarisParser.PairContext res : item.value().map().pair()) {
                                retval.effect += "£" + res.key() + "£" + " +" + gs(res) + " ";
                            }
                        }
                    }
                    break;
                }
                case "planet_modifier": {
                    for (StellarisParser.PairContext res : pair.value().map().pair()) {
                        String val = gs(res).trim();
                        if(!val.startsWith("-")) val = "+" + val;
                        String key = res.key();
                        key = key.replace("_add","").replace("planet_","");
                        retval.effect +=  "£"  + key + "£" + " " + val + " ";
                    }
                    break;
                }
            }
        }

        return retval;
    }
}
