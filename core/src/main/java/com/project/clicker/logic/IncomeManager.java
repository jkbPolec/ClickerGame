package com.project.clicker.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class IncomeManager {
    private final GameState state;
    private double passiveIncomeMultiplier;
    private double moneyPerClickMultiplier;
    private float timer = 0f;

    public IncomeManager(GameState state) {
        this.state = state;
        passiveIncomeMultiplier = 1;
        moneyPerClickMultiplier = 1;
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= 1f) {
            System.out.println(calculatePassiveIncome());
            int ticks = (int)(timer);
            state.addMoney(ticks * calculatePassiveIncome());
            timer -= ticks;
        }

        // Debugging: Press SPACE to add 100000 money
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            state.addMoney(100000);
        }
    }


    public void addMoneyFromClick() {
        state.addMoney(calculateMoneyPerClick());
    }


    public void increasePassiveIncome(double value) {
        passiveIncomeMultiplier += value;
    }

    public void increaseMoneyPerClick(double value) {
        moneyPerClickMultiplier +=  value;
    }

    public long calculateMoneyPerClick() {
        long value = 1;
        value *= (long) moneyPerClickMultiplier;
        return value;
    }

    public long getPassiveIncome() {
        return calculatePassiveIncome();
    }

    public long calculatePassiveIncome() {
        long value = 10;
        value *= (long) passiveIncomeMultiplier;
        return value;
    }
}
