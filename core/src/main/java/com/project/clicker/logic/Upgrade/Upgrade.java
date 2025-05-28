package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.IncomeManager;
import com.project.clicker.logic.PopulationManager;

import java.util.Map;



public class Upgrade {
    private String name;
    private Map<UpgradeType, Double> effects;
    private long cost;
    boolean active;

    public Upgrade(String name, Map<UpgradeType, Double> effects, long cost, GameState gameState) {
        if (effects.containsKey(UpgradeType.INCREASE_POPULATION)) {
            double value = effects.get(UpgradeType.INCREASE_POPULATION);
            if (value != Math.floor(value)) {
                throw new IllegalArgumentException("Population increase must be an integer value.");
            }
        }
        this.name = name;
        this.effects = effects;
        this.cost = cost;
        this.active = false;
        gameState.addUpgrade(this);

    }

    public String getName() {
        return name;
    }
    public void apply(IncomeManager incomeManager, PopulationManager populationManager) {
        active = true;
        for (Map.Entry<UpgradeType, Double> entry : effects.entrySet()) {
            switch (entry.getKey()) {
                case CLICK_INCOME:
                    incomeManager.increaseMoneyPerClick(entry.getValue());
                    break;
                case PASSIVE_INCOME:
                    incomeManager.changePassiveIncome(entry.getValue());
                    break;
                case INCREASE_POPULATION:
                    populationManager.increasePopulation(entry.getValue().intValue());
                    break;
            }
        }
    }
}
