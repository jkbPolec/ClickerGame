package com.project.clicker.logic;

public class IncomeManager {
    private GameState state;
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
        value *= moneyPerClickMultiplier;
        return value;
    }

    public long getPassiveIncome() {
        return calculatePassiveIncome();
    }

    public long calculatePassiveIncome() {
        long value = 10;
        value *= passiveIncomeMultiplier;
        return value;
    }
}
