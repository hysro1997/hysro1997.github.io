package net.turanar.stellaris.domain;

public enum GameObject {
    SB_BUILDING("Starbase Building", "common/starbase_buildings", "sm_"),
    ARMY("Army", "common/armies"),
    COMPONENT("Component","common/component_templates"),
    EDICTS("Edict", "common/edicts","edict_"),
    BUILDING("Building", "common/buildings"),
    STRAT_RESS("Reveals Resource","common/strategic_resources"),
    SB_MODULE("Starbase Module", "common/starbase_modules","sm_"),
    POLICY("Policy","common/policies","policy_"),
    //BUILDABLE_POP("Buildable Pop","common/buildable_pops"),
    //TILE_BLOCKER("Clear Blockers", "common/tile_blockers"),
    SHIP_SIZE("Ship Size", "common/ship_sizes","","00_ship_sizes.txt"),
    STARBASE("Starbase Upgrade", "common/ship_sizes","","00_starbases.txt");

    public String folder;
    public String label;
    public String locale_prefix = "";
    public String filter = ".txt";

    GameObject(String label, String folder) {
        this.folder = folder;
        this.label = label;
        this.locale_prefix = "";
    }

    GameObject(String label, String folder, String locale_prefix) {
        this.folder = folder;
        this.label = label;
        this.locale_prefix = locale_prefix;
    }


    GameObject(String label, String folder, String locale_prefix, String filter) {
        this.folder = folder;
        this.label = label;
        this.locale_prefix = locale_prefix;
        this.filter = filter;
    }
}
