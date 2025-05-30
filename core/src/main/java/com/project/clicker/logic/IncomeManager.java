package com.project.clicker.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class IncomeManager {
    private final GameState state;
    private int basicPassiveIncome;
    private int basicPerClickIncome;
    private int factoryIncome;
    private double factoryIncomeMultiplier;
    private double basicPerClickIncomeMultiplier;
    private double passiveIncomeMultiplier;
    private double moneyPerClickMultiplier;
    private float timer = 0f;

    public IncomeManager(GameState state) {
        this.state = state;
        factoryIncome = 0;
        factoryIncomeMultiplier = 1;
        basicPerClickIncomeMultiplier = 1;
        passiveIncomeMultiplier = 1;
        moneyPerClickMultiplier = 1;
        basicPassiveIncome = 10;
        basicPerClickIncome = 1;
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


    public void increasePassiveIncomeMultiplier(double value) {
        passiveIncomeMultiplier += value;
    }

    public void increaseMoneyPerClickMultiplier(double value) {
        moneyPerClickMultiplier +=  value;
    }

    public void increaseFactoryIncome(double value) {
        factoryIncome += value;
    }

    public void increaseFactoryIncomeMultiplier(double value) {
        factoryIncomeMultiplier += value;
    }

    public void increaseBasicPerClickIncomeMultiplier(double value) {
        basicPerClickIncomeMultiplier += value;
    }



    public long getPassiveIncome() {
        return calculatePassiveIncome();
    }

    public void increaseBasicPassiveIncome(int value) {
        basicPassiveIncome += value;
    }

    public void increaseBasicPerClickIncome(int value) {
        basicPerClickIncome += value;
    }

    private long calculatePassiveIncome() {
        long value = basicPassiveIncome + (long)(factoryIncome * factoryIncomeMultiplier);
        value = (long)(value * passiveIncomeMultiplier);

        return  value;
    }

    private long calculateMoneyPerClick() {
        long value = (long)(basicPerClickIncome * basicPerClickIncomeMultiplier);
        value = (long)(value * moneyPerClickMultiplier);
        return value;
    }
}
