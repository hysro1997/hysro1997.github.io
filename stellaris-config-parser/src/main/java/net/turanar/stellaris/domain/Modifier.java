package net.turanar.stellaris.domain;

import net.turanar.stellaris.antlr.StellarisParser;

import java.util.ArrayList;
import java.util.List;

public class Modifier {
    public Float factor = null;
    public Integer add = null;
    public ModifierType type;
    public String value;
    public transient StellarisParser.PairContext pair;

    public List<Modifier> children = new ArrayList<>();

    @Override
    public String toString() {
        return type != null && pair != null ? type.parse(pair) : (pair != null ? pair.getText() : "");
    }
}