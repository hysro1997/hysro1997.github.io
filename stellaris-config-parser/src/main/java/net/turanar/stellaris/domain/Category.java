package net.turanar.stellaris.domain;

import com.google.gson.annotations.SerializedName;

public enum Category {
    // physics
    粒子物理, @SerializedName("力场控制") 力场操控, 计算技术,
    // society
    生物学, @SerializedName("军事理论") 军事理论, @SerializedName("殖民学说") 新世界理论, 治国术, 灵能理论,
    // engineering
    工业技术, 材料科学, 推进力学, 宇航技术;

    public static Category eval(String name) {

        name = name.replaceAll(" ","");
        return Category.valueOf(name);
    }
}
