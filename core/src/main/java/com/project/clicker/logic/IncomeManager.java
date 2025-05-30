package com.project.clicker.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class IncomeManager {
    private final GameState state;
    private final IncomeMultipliers multipliers = new IncomeMultipliers();
    private int basicPassiveIncome = 10;
    private int basicPerClickIncome = 1;
    private int factoryIncome = 0;

    private float timer = 0f;

    public IncomeManager(GameState state) {
        this.state = state;
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= 1f) {
            //System.out.println(calculatePassiveIncome());
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


    public void increasePassiveIncomeMultiplier(double value) {
        multipliers.passive += value;
    }

    public void increaseMoneyPerClickMultiplier(double value) {
        multipliers.perClick +=  value;
    }

    public void increaseFactoryIncome(double value) {
        factoryIncome += value;
    }

    public void increaseFactoryIncomeMultiplier(double value) {
        multipliers.factory += value;
    }


    public void increaseIncomeFromShopsMultiplier(double value) {
        multipliers.shops *= value;
    }

    public long getPassiveIncome() {
        return calculatePassiveIncome();
    }


    private long calculatePassiveIncome() {
        long value = basicPassiveIncome;
        value += (long)(factoryIncome * multipliers.factory);
        value += (long)(state.getPopulation() * state.getShopsNumber() * multipliers.shops);
        value = (long)(value * multipliers.passive);
        return value;
    }

    private long calculateMoneyPerClick() {
        long value = basicPerClickIncome;
        value = (long)(value * multipliers.perClick);

        return value;
    }


}
