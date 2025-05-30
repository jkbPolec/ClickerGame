package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.IncomeManager;
import com.project.clicker.logic.PopulationManager;

public abstract class Upgrade {
    protected String name;
    protected long cost;
    protected double costIncrease;
    protected boolean active;
    protected int timesActivated = 0;

    protected IncomeManager incomeManager;
    protected PopulationManager populationManager;
    protected GameState state;

    public Upgrade(String name, long cost, double costIncrease,
                   GameState state, IncomeManager incomeManager, PopulationManager populationManager) {
        this.name = name;
        this.cost = cost;
        this.costIncrease = costIncrease;
        this.active = false;
        this.incomeManager = incomeManager;
        this.populationManager = populationManager;
        this.state = state;
        state.addUpgrade(this);
    }

    public long getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public abstract String getUpgradeInfo();
    public abstract void apply();
}
