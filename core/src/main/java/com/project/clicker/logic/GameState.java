package com.project.clicker.logic;
import com.project.clicker.logic.Upgrade.Upgrade;

import java.util.ArrayList;
import java.util.List;


public class GameState {
    private long money;
    private long clicks;
    private long population;
    private List<Upgrade> upgrades;
    private int factoriesNumber;

    public GameState() {
        this.money = 0;
        this.clicks = 0;
        this.population = 0;
        this.factoriesNumber = 0;
        this.upgrades = new ArrayList<>();
    }

    public void getFactioriesNumber() {
        this.factoriesNumber++;
    }

    public void addFactory(int value) {
        this.factoriesNumber += value;
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

    public long getPopulation() {
        return population;
    }

    public void addPopulation(long population) {
        this.population += population;
    }

    public void addUpgrade(Upgrade upgrade) {
        this.upgrades.add(upgrade);
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }
}
