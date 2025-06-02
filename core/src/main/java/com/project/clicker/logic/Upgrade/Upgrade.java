package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeManager;
import com.project.clicker.logic.managers.PopulationManager;
import com.project.clicker.logic.BigNumber;

public abstract class Upgrade {
    protected String name;
    protected BigNumber cost;
    protected BigNumber originalCost;
    protected double costIncrease;
    protected boolean active;
    protected int timesActivated = 0;

    protected IncomeManager incomeManager;
    protected PopulationManager populationManager;
    protected GameState state;

    public Upgrade(String name, BigNumber cost, double costIncrease,
                   GameState state, IncomeManager incomeManager, PopulationManager populationManager) {
        this.name = name;
        this.cost = cost;
        this.originalCost = cost;
        this.costIncrease = costIncrease;
        this.active = false;
        this.incomeManager = incomeManager;
        this.populationManager = populationManager;
        this.state = state;
        state.addUpgrade(this);
    }

    // Konstruktor dla kompatybilności z long
    public Upgrade(String name, long cost, double costIncrease,
                   GameState state, IncomeManager incomeManager, PopulationManager populationManager) {
        this(name, new BigNumber(cost), costIncrease, state, incomeManager, populationManager);
    }

    public void reset() {
        cost = originalCost;
        timesActivated = 0;
        active = false;
    }

    public BigNumber getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    protected void increaseCost() {
        cost = cost.multiply(1 + costIncrease);
    }

    public boolean canAfford() {
        return state.getMoney().isGreaterOrEqual(cost);
    }
    public abstract UpgradeInfo getUpgradeInfo();
    public abstract void apply();
}
