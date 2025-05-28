package com.project.clicker;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.IncomeManager;
import com.project.clicker.logic.PopulationManager;
import com.project.clicker.logic.Upgrade.Upgrade;
import com.project.clicker.logic.Upgrade.UpgradeType;

import java.util.HashMap;
import java.util.Map;


public class UpgradeFactory {
    private GameState gameState;
    private IncomeManager incomeManager;
    private PopulationManager populationManager;

    public UpgradeFactory(GameState gameState, IncomeManager incomeManager, PopulationManager populationManager) {
        this.gameState = gameState;
        this.incomeManager = incomeManager;
        this.populationManager = populationManager;
    }

    public void initializeUpgrades() {
        // Ulepszenie zwiększające dochód z kliknięcia
        Map<UpgradeType, Double> doubleClickEffects = new HashMap<>();
        doubleClickEffects.put(UpgradeType.CLICK_INCOME, 2.0);
        new Upgrade("Double Click", doubleClickEffects, 100, gameState);

        // Ulepszenie zwiększające pasywny dochód
        Map<UpgradeType, Double> passiveIncomeEffects = new HashMap<>();
        passiveIncomeEffects.put(UpgradeType.PASSIVE_INCOME, 2.0);
        new Upgrade("Passive Income Boost", passiveIncomeEffects, 250, gameState);

        // Ulepszenie zwiększające populację
        Map<UpgradeType, Double> populationEffects = new HashMap<>();
        populationEffects.put(UpgradeType.INCREASE_POPULATION, 10.0);
        new Upgrade("Population Growth", populationEffects, 500, gameState);

        // Można dodać więcej ulepszeń...
    }

    // Metoda do aplikowania konkretnego ulepszenia
    public void applyUpgrade(Upgrade upgrade) {
        upgrade.apply(incomeManager, populationManager);
    }
}
