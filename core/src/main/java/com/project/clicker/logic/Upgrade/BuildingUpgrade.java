package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.IncomeManager;
import com.project.clicker.logic.PopulationManager;


public class BuildingUpgrade extends Upgrade {
    BuildingUpgradeType type;

    public BuildingUpgrade(String name, long cost, double costIncrease, GameState state, IncomeManager incomeManager, PopulationManager populationManager, BuildingUpgradeType type) {
        super(name, cost, costIncrease, state, incomeManager, populationManager);
        this.type = type;
    }

    @Override
    public String getUpgradeInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Nazwa: ").append(name).append("\n");
        info.append("Koszt: ").append(cost).append("\n");
        info.append("Liczba użyć: ").append(timesActivated).append("\n");
        return info.toString();
    }

    @Override
    public void apply() {
        if (cost <= state.getMoney()) {
            active = true;
            timesActivated++;
            state.addMoney(-cost);
            cost += (long) (costIncrease * cost);


            switch (type) {
                case FACTORY:
                    state.addFactory(1);
                    incomeManager.increaseFactoryIncome(1);
                    break;
                case SHOP:
                    state.addShop(1);
                    break;
                case APARTMENT:
                    state.addApartment(1);
                    populationManager.increasePopulation(500);
                    break;
            }
        }
    }
}
