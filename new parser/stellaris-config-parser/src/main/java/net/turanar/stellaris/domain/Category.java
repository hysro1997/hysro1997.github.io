package net.turanar.stellaris.domain;

import com.google.gson.annotations.SerializedName;

public enum Category {
    // physics
    Particles, @SerializedName("Field Manipulation") Field_Manipulation, Computing,
    // society
    Biology, @SerializedName("Military Theory") Military_Theory, @SerializedName("New Worlds") New_Worlds, Statecraft, Psionics,
    // engineering
    Industry, Materials, Propulsion, Voidcraft;
}
