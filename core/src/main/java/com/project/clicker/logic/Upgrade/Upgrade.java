package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.IncomeManager;
import com.project.clicker.logic.PopulationManager;

import java.util.Map;



public class Upgrade {
    private String name;
    private Map<UpgradeType, Double> effects;
    private long cost;
    private boolean active;
    private int timesActivated = 0;
    private IncomeManager incomeManager;
    private PopulationManager populationManager;
    private GameState state;

    public Upgrade(String name, Map<UpgradeType, Double> effects, long cost, GameState state, IncomeManager incomeManager, PopulationManager populationManager) {
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
        this.incomeManager = incomeManager;
        this.populationManager = populationManager;
        this.state = state;
        state.addUpgrade(this);

    }

    public String getUpgradeInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Nazwa: ").append(name).append("\n");
        info.append("Koszt: ").append(cost).append("\n");
        info.append("Efekty:\n");

        for (Map.Entry<UpgradeType, Double> entry : effects.entrySet()) {
            info.append(" - ")
                .append(entry.getKey().toString()) // lub .name()
                .append(": ")
                .append(entry.getValue())
                .append("\n");
        }
        info.append("Liczba użyć: ").append(timesActivated).append("\n");
        return info.toString();
    }

    public long getCost() {
        return cost;
    }

    public Map<UpgradeType, Double> getEffects() {
        return effects;
    }

    public void apply() {
        active = true;
        timesActivated++;
        state.addMoney(-cost);
        cost += (long) (cost * 0.2);

        for (Map.Entry<UpgradeType, Double> entry : effects.entrySet()) {
            switch (entry.getKey()) {
                case CLICK_INCOME:
                    incomeManager.increaseMoneyPerClick(entry.getValue());
                    break;
                case PASSIVE_INCOME:
                    incomeManager.increasePassiveIncome(entry.getValue());
                    break;
                case INCREASE_POPULATION:
                    populationManager.increasePopulation(entry.getValue().intValue());
                    break;
            }
        }
        if ((timesActivated + 1) % 10 == 0) {
            for (Map.Entry<UpgradeType, Double> entry : effects.entrySet()) {
                entry.setValue(entry.getValue() * 2);
            }
            if (!name.endsWith(" !BONUS!")) {
                name += " !BONUS!";
            }
        } else {
            if (name.endsWith(" !BONUS!")) {
                for (Map.Entry<UpgradeType, Double> entry : effects.entrySet()) {
                    entry.setValue(entry.getValue() / 2);
                }
                name = name.substring(0, name.length() - " !BONUS!".length());
            }
        }
    }
}
