package net.turanar.stellaris.domain;

public enum GameObject {
    SB_BUILDING("太空港建筑", "common/starbase_buildings", "sm_"),
    ARMY("陆军", "common/armies"),
    COMPONENT("部件","common/component_templates"),
    EDICTS("道德", "common/edicts","edict_"),
    BUILDING("建筑", "common/buildings"),
    STRAT_RESS("揭示资源","common/strategic_resources"),
    SB_MODULE("太空港模块", "common/starbase_modules","sm_"),
    POLICY("政策","common/policies","policy_"),
    DECISION("法令","common/decisions","","02_special_decisions.txt"),

    //BUILDABLE_POP("Buildable Pop","common/buildable_pops"),
    //TILE_BLOCKER("Clear Blockers", "common/tile_blockers"),
    SHIP_SIZE("舰船大小", "common/ship_sizes","","00_ship_sizes.txt"),
    STARBASE("太空港升级", "common/ship_sizes","","00_starbases.txt"),
    MEGASTRUCTURE("巨构", "common/megastructures");


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
