package com.project.clicker.logic.items.effects;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.items.IItemEffect;

public class IncreaseClickMoneyEffect implements IItemEffect {
    private final double multiplier;

    public IncreaseClickMoneyEffect(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void apply(GameState state) {
        //TODO implementacja zwiększania pieniędzy za klik
    }


    @Override
    public String getDescription() {
        return "Zwiększa pieniądze za klik x" + multiplier;
    }
}
