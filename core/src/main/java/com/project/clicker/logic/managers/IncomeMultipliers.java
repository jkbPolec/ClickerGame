package com.project.clicker.logic.managers;

/**
 * Holds and manages all income multiplier values for different income sources.
 * These multipliers are applied to base income calculations to determine final income amounts.
 */
public class IncomeMultipliers {
    // Default multiplier values
    private static final double DEFAULT_FACTORY_MULTIPLIER = 1.0;
    private static final double DEFAULT_PER_CLICK_MULTIPLIER = 1.0;
    private static final double DEFAULT_SHOPS_MULTIPLIER = 0.002;
    private static final double DEFAULT_PASSIVE_MULTIPLIER = 1.0;

    // Multiplier fields
    public double factory;
    public double perClick;
    public double shops;
    public double passive;

    public IncomeMultipliers() {
        resetToDefaults();
    }

    /**
     * Returns a formatted string containing all multiplier information.
     * @return formatted string with all multiplier values
     */
    public String getMultipliersInfo() {
        return String.format(
            "Factory multiplier: %.2f%n" +
                "Per-click multiplier: %.2f%n" +
                "Shops multiplier: %.3f%n" +
                "Passive multiplier: %.2f%n",
            factory, perClick, shops, passive
        );
    }

    /**
     * Resets all multipliers to their default values.
     */
    public void resetToDefaults() {
        factory = DEFAULT_FACTORY_MULTIPLIER;
        perClick = DEFAULT_PER_CLICK_MULTIPLIER;
        shops = DEFAULT_SHOPS_MULTIPLIER;
        passive = DEFAULT_PASSIVE_MULTIPLIER;
    }



}
