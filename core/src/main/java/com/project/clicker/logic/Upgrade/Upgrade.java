package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeManager;
import com.project.clicker.logic.managers.PopulationManager;
import com.project.clicker.logic.BigNumber;

/**
 * Abstract base class for all upgrades in the game.
 * Provides common functionality for cost management, affordability checks, and state tracking.
 */
public abstract class Upgrade {
    protected String name;
    protected BigNumber cost;
    protected final BigNumber originalCost;
    protected final double costIncrease;
    protected boolean active;
    protected int timesActivated;

    protected final IncomeManager incomeManager;
    protected final PopulationManager populationManager;
    protected final GameState gameState;

    /**
     * Creates an upgrade with BigNumber cost.
     *
     * @param name the display name of the upgrade
     * @param cost the initial cost of the upgrade
     * @param costIncrease the multiplier for cost increase after each purchase (e.g., 0.15 for 15% increase)
     * @param gameState the game state to modify
     * @param incomeManager the income manager for income modifications
     * @param populationManager the population manager for population modifications
     */
    public Upgrade(String name, BigNumber cost, double costIncrease,
                   GameState gameState, IncomeManager incomeManager, PopulationManager populationManager) {
        this.name = name;
        this.cost = new BigNumber(cost);
        this.originalCost = new BigNumber(cost);
        this.costIncrease = costIncrease;
        this.active = false;
        this.timesActivated = 0;
        this.incomeManager = incomeManager;
        this.populationManager = populationManager;
        this.gameState = gameState;

        // Register this upgrade with the game state
        gameState.addUpgrade(this);
    }

    /**
     * Creates an upgrade with long cost (for compatibility).
     *
     * @param name the display name of the upgrade
     * @param cost the initial cost of the upgrade as a long value
     * @param costIncrease the multiplier for cost increase after each purchase
     * @param gameState the game state to modify
     * @param incomeManager the income manager for income modifications
     * @param populationManager the population manager for population modifications
     */
    public Upgrade(String name, long cost, double costIncrease,
                   GameState gameState, IncomeManager incomeManager, PopulationManager populationManager) {
        this(name, new BigNumber(cost), costIncrease, gameState, incomeManager, populationManager);
    }

    /**
     * Resets the upgrade to its initial state.
     * Restores original cost, clears activation count, and sets inactive.
     */
    public void reset() {
        cost = new BigNumber(originalCost);
        timesActivated = 0;
        active = false;
    }

    /**
     * Gets the current cost of the upgrade.
     *
     * @return the current cost as a BigNumber
     */
    public BigNumber getCost() {
        return new BigNumber(cost);
    }

    /**
     * Gets the original cost of the upgrade (before any increases).
     *
     * @return the original cost as a BigNumber
     */
    public BigNumber getOriginalCost() {
        return new BigNumber(originalCost);
    }

    /**
     * Gets the display name of the upgrade.
     *
     * @return the upgrade name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the cost increase multiplier.
     *
     * @return the cost increase multiplier
     */
    public double getCostIncrease() {
        return costIncrease;
    }

    /**
     * Checks if the upgrade is currently active.
     *
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets the number of times this upgrade has been activated.
     *
     * @return the activation count
     */
    public int getTimesActivated() {
        return timesActivated;
    }

    /**
     * Increases the cost of the upgrade based on the cost increase multiplier.
     * New cost = current cost × (1 + costIncrease)
     */
    protected void increaseCost() {
        cost = cost.multiply(1.0 + costIncrease);
    }

    /**
     * Checks if the player can afford this upgrade.
     *
     * @return true if the player has enough money, false otherwise
     */
    public boolean canAfford() {
        return gameState.getMoney().isGreaterOrEqual(cost);
    }

    /**
     * Calculates what the cost would be after a certain number of purchases.
     *
     * @param additionalPurchases the number of additional purchases to simulate
     * @return the projected cost after the specified purchases
     */
    public BigNumber getProjectedCost(int additionalPurchases) {
        if (additionalPurchases < 0) {
            throw new IllegalArgumentException("Additional purchases cannot be negative");
        }

        BigNumber projectedCost = new BigNumber(cost);
        for (int i = 0; i < additionalPurchases; i++) {
            projectedCost = projectedCost.multiply(1.0 + costIncrease);
        }
        return projectedCost;
    }

    /**
     * Gets upgrade information for display purposes.
     *
     * @return upgrade information object
     */
    public abstract UpgradeInfo getUpgradeInfo();

    /**
     * Applies the upgrade effect, purchasing it if affordable.
     * Implementations should check affordability and handle all purchase logic.
     */
    public abstract void apply();

}
