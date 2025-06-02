package com.project.clicker.logic.managers;

import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.Upgrade.Upgrade;

/**
 * Manages game resets and special point purchases (prestige system).
 * Handles resetting game state while preserving certain progress elements.
 */
public class ResetManager {
    private static final BigNumber BASE_SPECIAL_POINTS_COST = new BigNumber(1000000);

    private final GameState gameState;
    private final IncomeManager incomeManager;
    private final IncomeMultipliers incomeMultipliers;

    public ResetManager(GameState gameState, IncomeManager incomeManager) {
        this.gameState = gameState;
        this.incomeManager = incomeManager;
        this.incomeMultipliers = incomeManager.getMultipliers();
    }

    /**
     * Performs a complete game reset, returning most progress to initial state.
     * This includes resetting income multipliers, game state, and all upgrades.
     */
    public void performReset() {
        incomeMultipliers.resetToDefaults();
        gameState.reset();
        incomeManager.resetBaseIncomeValues();

        for (Upgrade upgrade : gameState.getUpgrades()) {
            upgrade.reset();
        }
    }

    /**
     * Attempts to purchase special points using accumulated money.
     * If successful, performs a reset and adds the special points to the player's total.
     *
     * @param quantity the number of special points to purchase
     * @return true if the purchase was successful, false if insufficient funds
     */
    public boolean purchaseSpecialPoints(int quantity) {
        if (quantity <= 0) {
            return false;
        }

        BigNumber totalCost = calculateSpecialPointsCost(quantity);

        if (!gameState.getMoney().isGreaterOrEqual(totalCost)) {
            return false;
        }

        gameState.subtractMoney(totalCost);
        gameState.addSpecialPoints(quantity);
        performReset();

        return true;
    }

    /**
     * Calculates the total cost for purchasing a specific quantity of special points.
     *
     * @param quantity the number of special points to calculate cost for
     * @return the total cost as a BigNumber
     */
    public BigNumber calculateSpecialPointsCost(int quantity) {
        if (quantity <= 0) {
            return BigNumber.ZERO();
        }

        return BASE_SPECIAL_POINTS_COST.multiply(quantity);
    }

    /**
     * Checks if the player can afford the specified quantity of special points.
     *
     * @param quantity the number of special points to check affordability for
     * @return true if the player has enough money, false otherwise
     */
    public boolean canAffordSpecialPoints(int quantity) {
        if (quantity <= 0) {
            return false;
        }

        BigNumber cost = calculateSpecialPointsCost(quantity);
        return gameState.getMoney().isGreaterOrEqual(cost);
    }

    /**
     * Gets the base cost for a single special point.
     *
     * @return the base cost as a BigNumber
     */
    public BigNumber getBaseSpecialPointsCost() {
        return new BigNumber(BASE_SPECIAL_POINTS_COST);
    }
}
