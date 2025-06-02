package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeManager;
import com.project.clicker.logic.managers.PopulationManager;
import com.project.clicker.logic.BigNumber;

public class BuildingUpgrade extends Upgrade {
    BuildingUpgradeType type;

    public BuildingUpgrade(String name, BigNumber cost, double costIncrease,
                           GameState state, IncomeManager incomeManager,
                           PopulationManager populationManager, BuildingUpgradeType type) {
        super(name, cost, costIncrease, state, incomeManager, populationManager);
        this.type = type;
    }

    // Konstruktor dla kompatybilności
    public BuildingUpgrade(String name, long cost, double costIncrease,
                           GameState state, IncomeManager incomeManager,
                           PopulationManager populationManager, BuildingUpgradeType type) {
        super(name, new BigNumber(cost), costIncrease, state, incomeManager, populationManager);
        this.type = type;
    }

    @Override
    public UpgradeInfo getUpgradeInfo() {
        return UpgradeInfo.fromBuilding(name, cost, type, timesActivated);
    }

    @Override
    public void apply() {
        if (canAfford()) {
            active = true;
            timesActivated++;
            state.subtractMoney(cost);
            increaseCost();

            switch (type) {
                case FACTORY:
                    state.addFactory(1);
                    break;
                case SHOP:
                    state.addShop(1);
                    break;
                case APARTMENT:
                    state.addApartment(1);
                    state.addPopulation(new BigNumber(1000));
                    break;
            }
        }
    }
}
