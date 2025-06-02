package com.project.clicker.logic.items.effects;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.items.IItemEffect;
import com.project.clicker.logic.managers.IncomeManager;
import jdk.internal.org.jline.reader.impl.history.DefaultHistory;

public class IncreaseShopMultiplierEffect implements IItemEffect {

    private final double multiplier;

    public IncreaseShopMultiplierEffect(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void apply(IncomeManager incomeManager) {
        System.out.println("Increase click money");
        incomeManager.multiplyBasicIncomeFromShops(multiplier);
    }

    @Override
    public String getDescription() {
        return "Zwiększa mnożnik sklepu o " + multiplier;
    }
}
