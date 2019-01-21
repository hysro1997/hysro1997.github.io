package net.turanar.stellaris.visitor;

import net.turanar.stellaris.domain.Area;
import net.turanar.stellaris.domain.Category;
import net.turanar.stellaris.domain.Modifier;
import net.turanar.stellaris.domain.Technology;
import net.turanar.stellaris.parser.StellarisBaseVisitor;
import net.turanar.stellaris.parser.StellarisParser;
import net.turanar.stellaris.parser.StellarisParser.PairContext;
import net.turanar.stellaris.parser.StellarisParser.ValueContext;

import static net.turanar.stellaris.Global.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TechnologyVisitor extends StellarisBaseVisitor<Technology> {
    @Override
    public Technology visitPair(StellarisParser.PairContext context) {
        Technology retval = new Technology();

        retval.key = context.key();
        retval.name = i18n(retval.key);
        retval.description = i18n(retval.key + "_desc");

        for(PairContext pair : context.value().map().pair()) {
            ValueContext val = pair.value();
            switch (pair.key()) {
                case "tier":
                    if(val.NUMBER() != null) {
                        retval.tier = Integer.valueOf(val.NUMBER().getText());
                    } else if (val.VARIABLE() != null) {
                        retval.tier = Integer.valueOf(variable(val.VARIABLE().getText()));
                    }
                    break;
                case "cost":
                    if(val.NUMBER() != null) {
                        retval.cost = Integer.valueOf(val.NUMBER().getText());
                    } else if (val.VARIABLE() != null) {
                        retval.cost = Integer.valueOf(variable(val.VARIABLE().getText()));
                    }
                    break;
                case "start_tech":
                    retval.is_start_tech = (val.BOOLEAN().getText().equals("yes"));
                    break;
                case "is_rare":
                    retval.is_rare = (val.BOOLEAN().getText().equals("yes"));
                    break;
                case "is_dangerous":
                    retval.is_dangerous = (val.BOOLEAN().getText().equals("yes"));
                    break;
                case "category":
                    String cat = val.array().value(0).getText();
                    cat = i18n(cat);
                    cat = cat.replaceAll(" ","_");
                    retval.category = Category.valueOf(cat);
                    break;
                case "prerequisites":
                    if(val.array() == null) break;
                    for(ValueContext preq : val.array().value()) {
                        if(preq.STRING() != null) {
                            retval.prerequisites.add(preq.STRING().getText().replaceAll("\"",""));
                        } else if(preq.BAREWORD() != null) {
                            retval.prerequisites.add(preq.BAREWORD().getText());
                        }
                    }
                    break;
                case "area":
                    retval.area = Area.valueOf(val.BAREWORD().getText());
                    break;
                case "weight":
                    if(val.NUMBER() != null) {
                        retval.base_weight = Float.valueOf(val.NUMBER().getText());
                    } else if (val.VARIABLE() != null) {
                        retval.base_weight = Float.valueOf(variable(val.VARIABLE().getText()));
                    }
                    break;
                case "weight_modifier":
                    WeightModifierVisitor visitor = new WeightModifierVisitor(retval);
                    retval.weight_modifiers.addAll(visitor.visitMap(val.map()));
                    break;
                case "potential":
                    WeightModifierVisitor visitor2 = new WeightModifierVisitor(retval);
                    retval.potential.addAll(visitor2.visitPotential(val.map()));
                    break;
                case "feature_flags":
                    for(ValueContext ff : val.array().value()) {
                        String feature = ff.BAREWORD().getText();
                        feature = "feature_" + feature;
                        feature = i18n(feature);
                        retval.feature_unlocks.add("<b>Feature : </b>" + feature);
                    }
                    break;
                case "modifier":
                    if(val.map() == null) break;
                    for(PairContext mod : val.map().pair()) {
                        if(mod.key().equals("BIOLOGICAL_species_trait_points_add")) continue;
                        if(mod.key().equals("description")) {
                            String key = mod.value().BAREWORD().getText();
                            String effect = i18n(key);
                            if(key.equals(effect)) effect = i18n("mod_" + key.toLowerCase());
                            effect = effect.replace("$POINTS|0=+$","+1");
                            retval.feature_unlocks.add(effect);
                        } else if (!mod.key().startsWith("description")){
                            String key = mod.key().toLowerCase();

                            if(key.equals("science_ship_survey_speed")) key = "mod_ship_science_survey_speed";
                            if(key.equals("ship_anomaly_generation_chance_mult")) key = "mod_ship_anomaly_generation_chance";
                            if(key.equals("ship_anomaly_research_speed_mult")) key = "mod_ship_anomaly_research_speed";
                            if(key.equals("all_technology_research_speed")) key = "all_tech_research_speed";

                            String effect = i18n(key);
                            if(key.equals("species_leader_exp_gain")) effect = "Species Leader Exp Gain";
                            if(key.equals(effect)) effect = i18n("mod_" + key);
                            if(effect.startsWith("mod_")) effect = i18n("mod_country_" + key);

                            String value = "";
                            if(mod.value() == null) {
                                value = mod.getText();
                            } else if (mod.value().NUMBER() != null) {
                                value = mod.value().NUMBER().getText();
                                if(value.contains(".")) {
                                    NumberFormat nf = new DecimalFormat("+#;-#");
                                    value = nf.format(Float.valueOf(value)*100.0f) + "%";
                                } else {
                                    value = '+' + value;
                                }
                            } else if (mod.value().BOOLEAN() != null) {
                                value = mod.value().BOOLEAN().getText();
                            }

                            //System.out.println("\t" + effect + " " + value);
                            retval.feature_unlocks.add(effect + " " + value);
                        }
                    }
                    break;
            }
        }

        return retval;
    }
}
