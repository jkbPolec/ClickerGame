package com.project.clicker.logic;

public class GameState {
    private long money;
    private long clicks;
    private long moneyPerSecond = 1;

    public void init() {
        money = 0;
        clicks = 0;
    }

    public long getMoney() {
        return money;
    }

    public void addMoney(long money) {
        this.money += money;
    }

    public long getClicks() {
        return clicks;
    }

    public void addClicks(long clicks) {
        this.clicks += clicks;
    }

    public long getMoneyPerSecond() {
        return moneyPerSecond;
    }

}
