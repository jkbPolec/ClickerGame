package com.project.clicker.logic;

public class IncomeManager {
    private GameState state;
    private long passiveIncome;
    private long moneyPerClick;
    private float timer = 0f;

    public IncomeManager(GameState state) {
        this.state = state;
        passiveIncome = 1;
        moneyPerClick = 1;
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= 1f) {
            System.out.println(passiveIncome);
            int ticks = (int)(timer);
            state.addMoney(ticks * passiveIncome);
            timer -= ticks;
        }
    }

    public void addMoneyFromClick() {
        state.addMoney(moneyPerClick);
    }


    public void changePassiveIncome(double value) {
        passiveIncome *= value;
    }

    public void increaseMoneyPerClick(double value) {
        moneyPerClick *= value;
    }

    public long getPassiveIncome() {
        return passiveIncome;
    }
}
