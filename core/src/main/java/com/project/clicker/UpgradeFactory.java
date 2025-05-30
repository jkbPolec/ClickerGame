package com.project.clicker;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.IncomeManager;
import com.project.clicker.logic.PopulationManager;
import com.project.clicker.logic.Upgrade.BuildingUpgrade;
import com.project.clicker.logic.Upgrade.BuildingUpgradeType;
import com.project.clicker.logic.Upgrade.NormalUpgrade;
import com.project.clicker.logic.Upgrade.NormalUpgradeType;

import java.util.HashMap;
import java.util.Map;


public class UpgradeFactory {
    private final GameState state;
    private final IncomeManager incomeManager;
    private final PopulationManager populationManager;

    public UpgradeFactory(GameState gameState, IncomeManager incomeManager, PopulationManager populationManager) {
        this.state = gameState;
        this.incomeManager = incomeManager;
        this.populationManager = populationManager;
    }

    public void initializeUpgrades() {
        // Ulepszenie zwiększające dochód z kliknięcia
        Map<NormalUpgradeType, Double> doubleClickEffects = new HashMap<>();
        doubleClickEffects.put(NormalUpgradeType.CLICK_INCOME, 0.1);
        new NormalUpgrade("Click Income Boost", doubleClickEffects, 10, 0.2, state, incomeManager, populationManager);

        // Ulepszenie zwiększające pasywny dochód
        Map<NormalUpgradeType, Double> passiveIncomeEffects = new HashMap<>();
        passiveIncomeEffects.put(NormalUpgradeType.PASSIVE_INCOME, 0.1);
        new NormalUpgrade("Passive Income Boost", passiveIncomeEffects, 10, 0.2, state, incomeManager, populationManager);

        // Ulepszenie nowa fabryka
        new BuildingUpgrade("New Factory", 100, 0.2, state, incomeManager, populationManager, BuildingUpgradeType.FACTORY);

        // Ulepszenie modifier fabryk
        Map<NormalUpgradeType, Double> factoryIncomeEffect = new HashMap<>();
        factoryIncomeEffect.put(NormalUpgradeType.FACTORY_INCOME, 2.0);
        new NormalUpgrade("Factory Produce Boost", factoryIncomeEffect, 10, 0.2, state, incomeManager, populationManager);

        Map<NormalUpgradeType, Double> superPassiveIncomeEffect = new HashMap<>();
        superPassiveIncomeEffect.put(NormalUpgradeType.FACTORY_INCOME, 2.0);
        superPassiveIncomeEffect.put(NormalUpgradeType.PASSIVE_INCOME, 2.0);
        new NormalUpgrade("Super Boost", superPassiveIncomeEffect, 10000, 2.0, state, incomeManager, populationManager);

        new BuildingUpgrade("New Apartment", 100, 0.2, state, incomeManager, populationManager, BuildingUpgradeType.APARTMENT);

        new BuildingUpgrade("New Shope", 100, 0.2, state, incomeManager, populationManager, BuildingUpgradeType.SHOP);

        Map<NormalUpgradeType, Double> shopIncomeEffect = new HashMap<>();
        shopIncomeEffect.put(NormalUpgradeType.SHOP_INCOME, 2.0);
        new NormalUpgrade("Shop Income Boost", shopIncomeEffect, 1000, 2.0, state, incomeManager, populationManager);
        // Można dodać więcej ulepszeń...
    }


}
