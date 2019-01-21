package net.turanar.stellaris.domain;

import net.turanar.stellaris.Global;
import net.turanar.stellaris.parser.StellarisParser.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import static net.turanar.stellaris.Global.*;

public enum ModifierType {
    has_ascension_perk("拥有 %s 飞升"),
    has_authority("拥有 %s 权力"),
    has_blocker("拥有 £blocker£ 地块: %s"),
    has_technology("拥有科技: %s"),
    has_valid_civic("拥有政体: %s"),
    has_civic("拥有政体: %s"),
    has_modifier("有 %s 修正"),
    has_ethic("有 %s 道德"),
    host_has_dlc("有 DLC %s"),
    has_tradition("拥有 %s 传统"),
    has_country_flag("拥有 %s 帝国标记"),
    has_global_flag("拥有 %s 全球标记"),
    has_deposit("拥有地块 %s"),
    is_country_type("有帝国类型: %s"),
    is_planet_class("是 %s"),
    has_communications("与我们帝国有交流"),
    pop_has_trait("Pop有特质 %s"),
    has_policy_flag((p) -> f("有政策 %s", i18n(gs(p) + "_name"))),
    owns_any_bypass((p) -> f("控制一个有  %s 的星系", i18n("bypass_" + gs(p).toLowerCase()))),
    has_seen_any_bypass((p) -> f("遇到过  %s", i18n("bypass_" + gs(p).toLowerCase()))),

    is_xenophile(DefaultParser.SCRIPTED),
    is_pacifist(DefaultParser.SCRIPTED),
    is_materialist(DefaultParser.SCRIPTED),
    is_spiritualist(DefaultParser.SCRIPTED),
    is_gestalt(DefaultParser.SCRIPTED),
    is_mechanical_empire(DefaultParser.SCRIPTED),
    is_regular_empire(DefaultParser.SCRIPTED),
    is_machine_empire(DefaultParser.SCRIPTED),
    is_hive_empire(DefaultParser.SCRIPTED),
    is_megacorp(DefaultParser.SCRIPTED),
    allows_slavery(DefaultParser.SCRIPTED),

    is_ai("Is AI|Is NOT AI", DefaultParser.SIMPLE_BOOLEAN),

    is_enslaved("Pop 被奴役|Pop 没有被奴役", DefaultParser.SIMPLE_BOOLEAN),
    is_sapient("Pop 是贤明的|Pop 不贤明", DefaultParser.SIMPLE_BOOLEAN),
    has_any_megastructure_in_empire("有任何巨构|没有任何巨构",DefaultParser.SIMPLE_BOOLEAN),
    always("总是|从不", DefaultParser.SIMPLE_BOOLEAN),

    years_passed("游戏开始后 %s %s 年", DefaultParser.SIMPLE_OPERATION),
    num_owned_planets("拥有的星球数 %s %s", DefaultParser.SIMPLE_OPERATION),
    num_communications("可外交帝国数 %s %s", DefaultParser.SIMPLE_OPERATION),
    has_level("技能等级 %s %s", DefaultParser.SIMPLE_OPERATION),

    any_neighbor_country("任何邻国", DefaultParser.CONDITIONAL),
    any_owned_planet("任何拥有的星球", DefaultParser.CONDITIONAL),
    any_planet_within_border("疆域内的任何星球", DefaultParser.CONDITIONAL),
    any_planet("任何星球", DefaultParser.CONDITIONAL),
    any_owned_pop("任何拥有的 Pop", DefaultParser.CONDITIONAL),
    any_system_within_border("疆域内的任何星系", DefaultParser.CONDITIONAL),
    any_relation("任何帝国间关系",DefaultParser.CONDITIONAL),
    any_pop("任何 Pop", DefaultParser.CONDITIONAL),
    owner_species("初始物种 :", DefaultParser.CONDITIONAL),

    NOR("全部为假", DefaultParser.CONDITIONAL),
    OR("至少一个为真", DefaultParser.CONDITIONAL),
    NAND("至少一个为假", DefaultParser.CONDITIONAL),
    AND("全部为真", DefaultParser.CONDITIONAL),

    has_trait((p) -> {
        String expertise = i18n(gs(p));
        if(expertise.contains("Expertise: ")) expertise = expertise.replaceAll("Expertise: ","") + " 专长";
        return "Is " + expertise;
    }),
    area((p) -> StringUtils.capitalize(gs(p))),
    research_leader((p) -> {
        String area = "";
        List<String> conditions = new ArrayList<>();
        for(PairContext prop : p.value().map().pair()) {
           Modifier m = visitCondition(prop);
           if(m.type.equals(ModifierType.area)) {
        	   area = m.toString();
           }
           else conditions.add(m.toString());
        }
        String type = "";
        switch (area) {
        case "Physics" : type = "物理学";break;
        case "Society" : type = "社会学";break;
        default : type = "工程学";
        }
        String retval = "科学家 (" + type + "): ";
        for(int i = 0; i < conditions.size(); i++) {
            retval = retval + "\n" + LS + conditions.get(i);
        }
        return retval;
    }),

    has_resource((p) -> {
        String type = "";
        String count = "";
        for(PairContext prop : p.value().map().pair()) {
            if(prop.key().equals("type")) {
                type = gs(prop);
            } else if (prop.key().equals("amount")) {
                count = op(prop) + " " + gs(prop);
            }
        }
        return "拥有 £" + type + "£ " + i18n(type) + " " +  count;
    }),

    count_starbase_sizes((p) -> {
        String retval = "%s 数量是 %s %s";
        String size = null, operator = null, count = null;
        for(PairContext prop : p.value().map().pair()) {
            if(prop.key().equals("starbase_size")) {
                size = i18n(gs(prop));
            } else if (prop.key().equals("count")) {
                operator = op(prop);
                count = gs(prop);
            }
        }
        return String.format(retval, size, operator, count);
    }),

    num_districts((p)->{
        String type = "";
        String count = "";
        for(PairContext prop : p.value().map().pair()) {
            if(prop.key().equals("type")) {
                type = i18n(gs(prop));
            } else if (prop.key().equals("value")) {
                count = op(prop) + " " + gs(prop);
            }
        }
        return  type + " 数量是 " + count;
    }),

    count_owned_pops((p) -> {
        String limits = "";
        String count = "";
        for(PairContext prop : p.value().map().pair()) {
            if(prop.key().equals("limit")) {
                for(PairContext l : prop.value().map().pair()) {
                    Modifier m = visitCondition(l);
                    limits += "\n" + LS + m.toString();
                }
            } else if(prop.key().equals("count")) {
                count = op(prop) + " " + gs(prop);
            }
        }
        return "拥有 " + count + "的Pop" + limits;
    }),

    NOT((p) -> {
        if(p.value().map().pair().size() > 1) return NOR.parser.apply(p);
        Modifier m = visitCondition(p.value().map().pair().get(0));
        if(m.type.equals(OR)) return NOR.parser.apply(p.value().map().pair().get(0));

        String retval = m.toString();
        if(retval.startsWith("Has")) {
            return "没 " + retval.replaceFirst("Has", "有");
        }
        else if(retval.startsWith("Is")) {
            return "不是 " + retval.replaceFirst("Is","");
        }  else if(retval.startsWith("Any")) {
            return retval.replaceFirst("Any","不是");
        } else {
            return "不是 " + retval;
        }
    }),

    DEFAULT((p) -> {
        String retval = p.getText();
        System.out.println(retval);
        return retval;
    })
    ;

    private static enum DefaultParser {
        SIMPLE((format,p) -> String.format(format,i18n(gs(p.value())))),
        SIMPLE_OPERATION((format,p) -> String.format(format, op(p), gs(p))),
        SIMPLE_BOOLEAN((format,p) -> {
           String[] sentence = format.split("\\|");
           if(gs(p).equals("yes")) return sentence[0]; else return sentence[1];
        }),
        CONDITIONAL((format, p) -> {
            List<String> conditions = new ArrayList<>();

            for(PairContext prop : p.value().map().pair()) {
                Modifier m = visitCondition(prop);
                conditions.add(m.toString());
            }

            String retval = format;
            for(int i = 0; i < conditions.size(); i++) {
                retval = retval + "\n" + LS + conditions.get(i).replaceAll(LS, "\t" + LS);
            }
            return retval;
        }),
        SCRIPTED((format, p) -> {
            PairContext q = SCRIPTED_TRIGGERS.get(p.key());
            boolean value = gs(p).equals("yes");
            List<String> conditions = new ArrayList<>();

            if(!value) {
              return ModifierType.NOT.parse(q);
            }

            for(PairContext prop : q.value().map().pair()) {
                Modifier m = visitCondition(prop);
                conditions.add(m.toString());
            }
            String retval = format;

            if(conditions.size() < 2) {
                retval = conditions.get(0);
                return retval;
            }

            for(int i = 0; i < conditions.size(); i++) {
                retval = retval + "\n" + LS + conditions.get(i).replaceAll(LS, "\t" + LS);
            }
            return retval;
        });

        private BiFunction<String, PairContext, String> parser;

        private DefaultParser(BiFunction<String, PairContext,String> parser) {
            this.parser = parser;
        }

        public String apply(String format, PairContext pair) {
            return this.parser.apply(format, pair);
        }
    }

    private Function<PairContext,String> parser;

    ModifierType(String format, DefaultParser parser) {
        this.parser = (p) -> parser.apply(format, p);
    }

    ModifierType(DefaultParser parser) {
        this.parser = (p) -> parser.apply(null, p);
    }

    ModifierType(Function<PairContext,String> parser) {
        this.parser = parser;
    }

    ModifierType(String format) {
        this(format, DefaultParser.SIMPLE);
    }

    public String parse(PairContext pair) {
        return parser.apply(pair);
    }

    public static ModifierType value(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return DEFAULT;
        }
    }

    public static Modifier visitCondition(PairContext pair) {
        Modifier retval = new Modifier();
        retval.type = ModifierType.value(pair.key());
        retval.pair = pair;
        return retval;
    }
}
