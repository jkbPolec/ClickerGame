package com.project.clicker;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.IncomeManager;
import com.project.clicker.logic.PopulationManager;
import com.project.clicker.logic.Upgrade.FactoryUpgrade;
import com.project.clicker.logic.Upgrade.NormalUpgrade;
import com.project.clicker.logic.Upgrade.Upgrade;
import com.project.clicker.logic.Upgrade.UpgradeType;

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
        Map<UpgradeType, Double> doubleClickEffects = new HashMap<>();
        doubleClickEffects.put(UpgradeType.CLICK_INCOME, 0.1);
        new NormalUpgrade("Click Income Boost", doubleClickEffects, 10, 0.2, state, incomeManager, populationManager);

        // Ulepszenie zwiększające pasywny dochód
        Map<UpgradeType, Double> passiveIncomeEffects = new HashMap<>();
        passiveIncomeEffects.put(UpgradeType.PASSIVE_INCOME, 0.1);
        new NormalUpgrade("Passive Income Boost", passiveIncomeEffects, 10, 0.2, state, incomeManager, populationManager);

        // Ulepszenie nowa fabryka
        new FactoryUpgrade("New Factory", 100, 0.2, state, incomeManager, populationManager);

        // Ulepszenie modifier fabryk
        Map<UpgradeType, Double> factoryIncomeEffect = new HashMap<>();
        factoryIncomeEffect.put(UpgradeType.FACTORY_INCOME, 2.0);
        new NormalUpgrade("Factory Produce Boost", factoryIncomeEffect, 10, 0.2, state, incomeManager, populationManager);

        Map<UpgradeType, Double> superPassiveIncomeEffect = new HashMap<>();
        superPassiveIncomeEffect.put(UpgradeType.FACTORY_INCOME, 2.0);
        superPassiveIncomeEffect.put(UpgradeType.PASSIVE_INCOME, 2.0);
        new NormalUpgrade("Super Boost", superPassiveIncomeEffect, 10000, 2.0, state, incomeManager, populationManager);

        // Można dodać więcej ulepszeń...
    }


}
