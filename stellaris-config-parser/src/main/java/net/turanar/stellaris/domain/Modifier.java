package net.turanar.stellaris.domain;

import net.turanar.stellaris.parser.StellarisParser.*;

import java.util.List;

public class Modifier {
    public ModifierType type;
    public PairContext pair;
    public List<Modifier> children;

    @Override
    public String toString() {
        return type != null && pair != null ? type.parse(pair) : (pair != null ? pair.getText() : "");
    }
}
