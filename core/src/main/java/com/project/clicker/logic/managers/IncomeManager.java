package com.project.clicker.logic.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;

/**
 * Manages all income calculations and processing for the clicker game.
 * Handles passive income, click income, and income from various sources like factories and shops.
 */
public class IncomeManager {
    private static final float PASSIVE_INCOME_TICK_RATE = 1.0f;
    private static final BigNumber DEBUG_MONEY_AMOUNT = BigNumber.TEN().pow(40);
    private static final BigNumber INITIAL_PASSIVE_INCOME = new BigNumber(10);

    private final GameState gameState;
    private final IncomeMultipliers multipliers;

    // Base income values
    private BigNumber basePassiveIncome;
    private BigNumber basePerClickIncome;
    private BigNumber baseShopsIncome;
    private BigNumber baseFactoryIncome;

    private float passiveIncomeTimer;

    public IncomeManager(GameState gameState) {
        this.gameState = gameState;
        this.multipliers = new IncomeMultipliers();
        this.passiveIncomeTimer = 0f;

        resetBaseIncomeValues();
    }

    /**
     * Updates the income manager, processing passive income over time.
     * Also handles debug input for testing purposes.
     */
    public void update(float deltaTime) {
        processPassiveIncome(deltaTime);
        handleDebugInput();
    }

    /**
     * Adds money to the game state based on click income calculation.
     */
    public void processClick() {
        BigNumber clickIncome = calculateClickIncome();
        gameState.addMoney(clickIncome);
    }

    /**
     * Resets all base income values to their initial state.
     */
    public void resetBaseIncomeValues() {
        basePassiveIncome = new BigNumber(INITIAL_PASSIVE_INCOME);
        basePerClickIncome = BigNumber.ONE();
        baseShopsIncome = BigNumber.ONE();
        baseFactoryIncome = BigNumber.ONE();
    }

    // === Income Modifiers ===

    public void multiplyBaseShopsIncome(double multiplier) {
        baseShopsIncome = baseShopsIncome.multiply(multiplier);
    }

    public void multiplyBaseClickIncome(double multiplier) {
        basePerClickIncome = basePerClickIncome.multiply(multiplier);
    }

    public void multiplyBaseFactoryIncome(double multiplier) {
        baseFactoryIncome = baseFactoryIncome.multiply(multiplier);
    }

    public void addPassiveIncomeMultiplier(double value) {
        multipliers.passive += value;
    }

    public void addClickIncomeMultiplier(double value) {
        multipliers.perClick += value;
    }

    public void addFactoryIncomeMultiplier(double value) {
        multipliers.factory += value;
    }

    public void multiplyShopsIncomeMultiplier(double multiplier) {
        multipliers.shops *= multiplier;
    }

    // === Getters ===

    public IncomeMultipliers getMultipliers() {
        return multipliers;
    }

    public BigNumber getCurrentPassiveIncome() {
        return calculatePassiveIncome();
    }

    public BigNumber getCurrentClickIncome() {
        return calculateClickIncome();
    }

    // === Private Methods ===

    private void processPassiveIncome(float deltaTime) {
        passiveIncomeTimer += deltaTime;

        if (passiveIncomeTimer >= PASSIVE_INCOME_TICK_RATE) {
            int completedTicks = (int) passiveIncomeTimer;
            BigNumber passiveIncomePerTick = calculatePassiveIncome();
            BigNumber totalPassiveIncome = passiveIncomePerTick.multiply(completedTicks);

            gameState.addMoney(totalPassiveIncome);
            passiveIncomeTimer -= completedTicks;
        }
    }

    private void handleDebugInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gameState.addMoney(DEBUG_MONEY_AMOUNT);
        }
    }

    private BigNumber calculatePassiveIncome() {
        BigNumber totalIncome = new BigNumber(basePassiveIncome);

        // Add factory contribution
        BigNumber factoryContribution = baseFactoryIncome
            .multiply(gameState.getFactoriesNumber())
            .multiply(multipliers.factory);
        totalIncome = totalIncome.add(factoryContribution);

        // Add shops contribution (population × shops × multiplier)
        BigNumber shopContribution = gameState.getPopulation()
            .multiply(gameState.getShopsNumber())
            .multiply(baseShopsIncome)
            .multiply(multipliers.shops);
        totalIncome = totalIncome.add(shopContribution);

        // Apply passive income multiplier
        totalIncome = totalIncome.multiply(multipliers.passive);

        return totalIncome;
    }

    private BigNumber calculateClickIncome() {
        return basePerClickIncome.multiply(multipliers.perClick);
    }
}
