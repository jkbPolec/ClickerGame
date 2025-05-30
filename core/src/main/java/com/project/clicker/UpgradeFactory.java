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

        // Dodatkowe ulepszenia w UpgradeFactory.java dla lepszego balansu:

        // Ulepszenie efektywności fabryk - drogie ale potężne
        Map<NormalUpgradeType, Double> factoryEfficiencyBoost = new HashMap<>();
        factoryEfficiencyBoost.put(NormalUpgradeType.FACTORY_INCOME, 1.5);
        new NormalUpgrade("Factory Efficiency", factoryEfficiencyBoost, 500, 0.5, state, incomeManager, populationManager);

        // Ulepszenie jakości apartamentów - bardzo drogie, dodaje więcej populacji
        Map<NormalUpgradeType, Double> apartmentQualityBoost = new HashMap<>();
        apartmentQualityBoost.put(NormalUpgradeType.PASSIVE_INCOME, 0.2);
        new NormalUpgrade("Luxury Apartments", apartmentQualityBoost, 5000, 1.8, state, incomeManager, populationManager);

        // Ulepszenie atrakcyjności sklepów
        Map<NormalUpgradeType, Double> shopAttractivenessBoost = new HashMap<>();
        shopAttractivenessBoost.put(NormalUpgradeType.SHOP_INCOME, 1.2);
        new NormalUpgrade("Shop Marketing", shopAttractivenessBoost, 2000, 0.6, state, incomeManager, populationManager);

        // === FABRYKI - Często kupowane, stabilny wzrost ===
        // Niska cena początkowa, mały wzrost kosztów
        new BuildingUpgrade("New Factory", 50, 0.3, state, incomeManager, populationManager, BuildingUpgradeType.FACTORY);

        // === APARTAMENTY - Rzadko kupowane, duży impact ===
        // Wysoka cena początkowa, znaczny wzrost kosztów
        new BuildingUpgrade("New Apartment", 1000, 1.8, state, incomeManager, populationManager, BuildingUpgradeType.APARTMENT);

        // === SKLEPY - Średnio często kupowane, zależne od populacji ===
        // Średnia cena, średni wzrost kosztów
        new BuildingUpgrade("New Shop", 200, 0.5, state, incomeManager, populationManager, BuildingUpgradeType.SHOP);



        // Można dodać więcej ulepszeń...
    }


}
