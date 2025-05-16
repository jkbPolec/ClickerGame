package com.project.clicker.logic;

public class IncomeManager {
    private GameState state;
    private float timer = 0f;

    public IncomeManager(GameState state) {
        this.state = state;
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= 1f) {
            int ticks = (int)(timer);
            state.addMoney(ticks * state.getMoneyPerSecond());
            timer -= ticks;
        }
    }
}
