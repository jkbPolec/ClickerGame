package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeManager;
import com.project.clicker.logic.managers.PopulationManager;

import java.util.Map;

public class NormalUpgrade extends Upgrade {
    Map<NormalUpgradeType, Double> effects;

    public NormalUpgrade(String name, Map<NormalUpgradeType, Double> effects, BigNumber cost, double costIncrease,
                         GameState state, IncomeManager incomeManager, PopulationManager populationManager) {
        super(name, cost, costIncrease, state, incomeManager, populationManager);
        this.effects = effects;
    }

    // Konstruktor dla kompatybilności z long (konwertuje na BigNumber)
    public NormalUpgrade(String name, Map<NormalUpgradeType, Double> effects, long cost, double costIncrease,
                         GameState state, IncomeManager incomeManager, PopulationManager populationManager) {
        super(name, new BigNumber(cost), costIncrease, state, incomeManager, populationManager);
        this.effects = effects;
    }

    @Override
    public UpgradeInfo getUpgradeInfo() {
        boolean isBonus = name.endsWith(" !BONUS!");
        return UpgradeInfo.fromNormal(name, cost, effects, timesActivated, isBonus);
    }

    public Map<NormalUpgradeType, Double> getEffects() {
        return effects;
    }

    @Override
    public void apply() {
        if (canAfford()) {
            active = true;
            timesActivated++;
            state.subtractMoney(cost);
            increaseCost();

            for (Map.Entry<NormalUpgradeType, Double> entry : effects.entrySet()) {
                switch (entry.getKey()) {
                    case CLICK_INCOME:
                        incomeManager.increaseMoneyPerClickMultiplier(entry.getValue());
                        break;
                    case PASSIVE_INCOME:
                        incomeManager.increasePassiveIncomeMultiplier(entry.getValue());
                        break;
                    case FACTORY_INCOME:
                        incomeManager.increaseFactoryIncomeMultiplier(entry.getValue());
                        break;
                    case SHOP_INCOME:
                        incomeManager.increaseIncomeFromShopsMultiplier(entry.getValue());
                }
            }

            updateBonusEffect();
        }
    }

    private void updateBonusEffect() {
        boolean shouldBeBonus = (timesActivated + 1) % 10 == 0;

        if (shouldBeBonus && !name.endsWith(" !BONUS!")) {
            for (Map.Entry<NormalUpgradeType, Double> entry : effects.entrySet()) {
                entry.setValue(entry.getValue() * 2);
            }
            name += " !BONUS!";
        } else if (!shouldBeBonus && name.endsWith(" !BONUS!")) {
            for (Map.Entry<NormalUpgradeType, Double> entry : effects.entrySet()) {
                entry.setValue(entry.getValue() / 2);
            }
            name = name.substring(0, name.length() - " !BONUS!".length());
        }
    }
}
