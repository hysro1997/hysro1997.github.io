package net.turanar.stellaris.domain;

import com.google.gson.annotations.SerializedName;

public enum Category {
    // physics
    粒子技术, @SerializedName("力场控制") 力场控制, 计算技术,
    // society
    生物科技, @SerializedName("军事理论") 军事理论, @SerializedName("殖民学说") 殖民学说, 治国之术, 心灵异能,
    // engineering
    工业技术, 材料科学, 推进技术, 太空技术;

    public static Category eval(String name) {

        name = name.replaceAll(" ","");
        return Category.valueOf(name);
    }
}
