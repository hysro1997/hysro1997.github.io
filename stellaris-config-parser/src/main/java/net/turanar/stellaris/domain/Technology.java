package net.turanar.stellaris.domain;

import com.google.gson.Gson;

import java.util.*;

public class Technology implements Comparable<Technology> {
    public String key;
    public Area area;
    public Float base_factor = 1.0f;
    public Float base_weight = 0.0f;
    public Category category;
    public Integer cost;
    public String description;
    public Set<String> feature_unlocks = new HashSet<String>();
    public boolean is_dangerous;
    public boolean is_rare = false;
    public boolean is_start_tech = false;
    public String  name;
    public List<String> prerequisites = new ArrayList<>();
    public Integer tier;
    public Integer index;
    public List<HashMap<String,String>> prerequisites_names = new ArrayList<>();
    public List<WeightModifier> weight_modifiers = new ArrayList<>();
    public List<Modifier> potential = new ArrayList<>();
    public SortedSet<Technology> children = new TreeSet<>();

    public boolean is_event = false;

    public Boolean is_gestalt = null;
    public Boolean is_megacorp = null;
    public Boolean is_machine_empire = null;
    public Boolean is_hive_empire = null;

    public Boolean is_drive_assimilator = null;
    public Boolean is_rogue_servitor = null;

    public Technology() {

    }

    @Override
    public String toString() {
        return this.key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Technology that = (Technology) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public int compareTo(Technology that) {
        final int EQUAL = 0;

        if (this == that) return EQUAL;
        if (this.equals(that)) return EQUAL;

        if(this.key.equals("tech_bio_reactor") || that.key.equals("tech_bio_reactor")) {
            System.out.println(this + " vs " + that);

        }
        int comparison = EQUAL;

        if(that.children.size() == 0 && this.children.size() > 0) comparison = -1;
        if(that.children.size() > 0 && this.children.size() == 0) comparison = 1;
        if (comparison != EQUAL) return comparison;

        comparison = this.area.compareTo(that.area);
        if (comparison != EQUAL) return comparison;

        comparison = this.category.compareTo(that.category);
        if (comparison != EQUAL) return comparison;

        if(this.tier == null) this.tier = 0;
        comparison = Integer.valueOf(this.tier).compareTo(Integer.valueOf(that.tier));
        if (comparison != EQUAL) return comparison;

        comparison = this.cost.compareTo(that.cost);
        if (comparison != EQUAL) return comparison;

        return 1;
    }
}
