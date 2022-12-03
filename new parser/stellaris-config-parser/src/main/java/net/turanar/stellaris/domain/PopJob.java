package net.turanar.stellaris.domain;

import java.util.Objects;

public class PopJob implements Comparable<PopJob> {
    public String key;
    public String name;
    public String building;
    public String effect;
    public String description;
    public String icon;
    public PopCategory category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PopJob popJob = (PopJob) o;
        return Objects.equals(key, popJob.key);
    }

    @Override
    public int hashCode() {
       return Objects.hash(key);
    }

    @Override
    public int compareTo(PopJob that) {
        final int EQUAL = 0;

        if (this == that) return EQUAL;

        int comparison = this.category.compareTo(that.category);
        if (comparison != EQUAL) return comparison;

        else return this.key.compareTo(that.key);
    }
}
