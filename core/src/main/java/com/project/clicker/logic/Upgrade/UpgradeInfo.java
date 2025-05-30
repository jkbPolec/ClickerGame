package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.BigNumber;

import java.util.Map;

public class UpgradeInfo {
    public String name;
    public BigNumber cost;
    public int timesActivated;
    public boolean isBonus;

    // Specyficzne dla NormalUpgrade
    public Map<NormalUpgradeType, Double> normalEffects;

    // Specyficzne dla BuildingUpgrade
    public BuildingUpgradeType buildingType;

    // Konstruktor dla NormalUpgrade
    public static UpgradeInfo fromNormal(String name, BigNumber cost, Map<NormalUpgradeType, Double> effects, int timesActivated, boolean isBonus) {
        UpgradeInfo info = new UpgradeInfo();
        info.name = name;
        info.cost = cost;
        info.normalEffects = effects;
        info.timesActivated = timesActivated;
        info.isBonus = isBonus;
        return info;
    }

    // Konstruktor dla BuildingUpgrade
    public static UpgradeInfo fromBuilding(String name, BigNumber cost, BuildingUpgradeType buildingType, int timesActivated) {
        UpgradeInfo info = new UpgradeInfo();
        info.name = name;
        info.cost = cost;
        info.buildingType = buildingType;
        info.timesActivated = timesActivated;
        info.isBonus = false; // bonus nie dotyczy BuildingUpgrade
        return info;
    }
}
