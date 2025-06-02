package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeManager;
import com.project.clicker.logic.managers.PopulationManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a standard upgrade that affects various game mechanics through multipliers.
 * Can become a bonus upgrade with doubled effects every 10th purchase.
 */
public class NormalUpgrade extends Upgrade {
    private static final String BONUS_SUFFIX = " !BONUS!";
    private static final int BONUS_ACTIVATION_INTERVAL = 10;
    private static final double BONUS_MULTIPLIER = 2.0;

    private final Map<NormalUpgradeType, Double> baseEffects;
    private final Map<NormalUpgradeType, Double> currentEffects;
    private boolean isBonusActive;

    /**
     * Creates a normal upgrade with BigNumber cost.
     *
     * @param name the display name of the upgrade
     * @param effects map of upgrade effects and their values
     * @param cost the initial cost of the upgrade
     * @param costIncrease the multiplier for cost increase after each purchase
     * @param gameState the game state to modify
     * @param incomeManager the income manager for income modifications
     * @param populationManager the population manager for population modifications
     */
    public NormalUpgrade(String name, Map<NormalUpgradeType, Double> effects, BigNumber cost, double costIncrease,
                         GameState gameState, IncomeManager incomeManager, PopulationManager populationManager) {
        super(name, cost, costIncrease, gameState, incomeManager, populationManager);
        this.baseEffects = new HashMap<>(effects);
        this.currentEffects = new HashMap<>(effects);
        this.isBonusActive = false;
    }

    /**
     * Creates a normal upgrade with long cost (for compatibility).
     *
     * @param name the display name of the upgrade
     * @param effects map of upgrade effects and their values
     * @param cost the initial cost of the upgrade as a long value
     * @param costIncrease the multiplier for cost increase after each purchase
     * @param gameState the game state to modify
     * @param incomeManager the income manager for income modifications
     * @param populationManager the population manager for population modifications
     */
    public NormalUpgrade(String name, Map<NormalUpgradeType, Double> effects, long cost, double costIncrease,
                         GameState gameState, IncomeManager incomeManager, PopulationManager populationManager) {
        this(name, effects, new BigNumber(cost), costIncrease, gameState, incomeManager, populationManager);
    }

    @Override
    public UpgradeInfo getUpgradeInfo() {
        return UpgradeInfo.fromNormal(name, cost, currentEffects, timesActivated, isBonusActive);
    }

    /**
     * Gets the current effects of this upgrade (may be modified by bonus).
     *
     * @return map of current upgrade effects
     */
    public Map<NormalUpgradeType, Double> getCurrentEffects() {
        return new HashMap<>(currentEffects);
    }

    /**
     * Gets the base effects of this upgrade (unmodified by bonus).
     *
     * @return map of base upgrade effects
     */
    public Map<NormalUpgradeType, Double> getBaseEffects() {
        return new HashMap<>(baseEffects);
    }

    /**
     * Checks if the bonus effect is currently active.
     *
     * @return true if bonus is active, false otherwise
     */
    public boolean isBonusActive() {
        return isBonusActive;
    }

    @Override
    public void apply() {
        if (!canAfford()) {
            return;
        }

        // Process the purchase
        active = true;
        timesActivated++;
        gameState.subtractMoney(cost);
        increaseCost();

        // Apply all upgrade effects
        applyUpgradeEffects();

        // Update bonus status
        updateBonusStatus();
    }

    /**
     * Applies all upgrade effects to the appropriate managers.
     */
    private void applyUpgradeEffects() {
        for (Map.Entry<NormalUpgradeType, Double> entry : currentEffects.entrySet()) {
            NormalUpgradeType effectType = entry.getKey();
            Double effectValue = entry.getValue();

            switch (effectType) {
                case CLICK_INCOME:
                    incomeManager.addClickIncomeMultiplier(effectValue);
                    break;

                case PASSIVE_INCOME:
                    incomeManager.addPassiveIncomeMultiplier(effectValue);
                    break;

                case FACTORY_INCOME:
                    incomeManager.addFactoryIncomeMultiplier(effectValue);
                    break;

                case SHOP_INCOME:
                    incomeManager.multiplyShopsIncomeMultiplier(effectValue);
                    break;

                default:
                    throw new IllegalStateException("Unknown upgrade effect type: " + effectType);
            }
        }
    }

    /**
     * Updates the bonus status and effects based on purchase count.
     * Bonus activates every 10th purchase and doubles all effects.
     */
    private void updateBonusStatus() {
        boolean shouldActivateBonus = timesActivated % BONUS_ACTIVATION_INTERVAL == 0;

        if (shouldActivateBonus && !isBonusActive) {
            activateBonus();
        } else if (!shouldActivateBonus && isBonusActive) {
            deactivateBonus();
        }
    }

    /**
     * Activates the bonus effect, doubling all upgrade effects and updating the name.
     */
    private void activateBonus() {
        isBonusActive = true;

        // Double all current effects
        for (Map.Entry<NormalUpgradeType, Double> entry : currentEffects.entrySet()) {
            entry.setValue(entry.getValue() * BONUS_MULTIPLIER);
        }

        // Update name if not already updated
        if (!name.endsWith(BONUS_SUFFIX)) {
            name += BONUS_SUFFIX;
        }
    }

    /**
     * Deactivates the bonus effect, restoring original effect values and name.
     */
    private void deactivateBonus() {
        isBonusActive = false;

        // Restore original effects
        currentEffects.clear();
        currentEffects.putAll(baseEffects);

        // Remove bonus suffix from name
        if (name.endsWith(BONUS_SUFFIX)) {
            name = name.substring(0, name.length() - BONUS_SUFFIX.length());
        }
    }

    @Override
    public void reset() {
        super.reset();

        // Reset bonus status and effects
        isBonusActive = false;
        currentEffects.clear();
        currentEffects.putAll(baseEffects);

        // Remove bonus suffix from name if present
        if (name.endsWith(BONUS_SUFFIX)) {
            name = name.substring(0, name.length() - BONUS_SUFFIX.length());
        }
    }

}
