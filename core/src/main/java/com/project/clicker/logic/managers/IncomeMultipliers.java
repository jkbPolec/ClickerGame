package com.project.clicker.logic.managers;

public class IncomeMultipliers {
    public double factory = 1.0;
    public double perClick = 1.0;
    public double shops = 0.002;
    public double passive = 1.0;

    public String getMultipliersInfo() {
        StringBuilder info = new StringBuilder();
        info
                .append("Factory multiplier: " + String.format("%.2f", factory) + "\n")
                .append("PerClick multiplier: " + String.format("%.2f", perClick) + "\n")
                .append("Shops multiplier: " + String.format("%.3f", shops) + "\n")
                .append("Passive multiplier: " + String.format("%.2f", passive) + "\n");
        return info.toString();
    }

    public void resetMultipliers() {
        factory = 1.0;
        perClick = 1.0;
        shops = 0.002;
        passive = 1.0;
    }
}
