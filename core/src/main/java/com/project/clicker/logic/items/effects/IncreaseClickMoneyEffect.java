package com.project.clicker.logic.items.effects;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.items.IItemEffect;
import com.project.clicker.logic.managers.IncomeManager;

public class IncreaseClickMoneyEffect implements IItemEffect {
    private final double multiplier;

    public IncreaseClickMoneyEffect(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void apply(IncomeManager incomeManager) {
        System.out.println("Increase click money");
        incomeManager.multiplyBaseClickIncome(multiplier);
    }


    @Override
    public String getDescription() {
        return "Increase click money by " + multiplier;
    }
}
