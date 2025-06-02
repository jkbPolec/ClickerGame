package com.project.clicker.logic;

import com.project.clicker.logic.Upgrade.Upgrade;
import com.project.clicker.logic.managers.IncomeManager;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private BigNumber money;
    private BigNumber clicks;
    private BigNumber population;
    private List<Upgrade> upgrades;
    private int factoriesNumber;
    private int shopsNumber;
    private int apartmentsNumber;
    private int specialPoints;
    private IncomeManager incomeManager;

    public GameState() {
        this.money = BigNumber.ZERO();
        this.clicks = BigNumber.ZERO();
        this.population = BigNumber.ZERO();
        this.factoriesNumber = 0;
        this.shopsNumber = 0;
        this.apartmentsNumber = 0;
        this.specialPoints = 0;
        this.upgrades = new ArrayList<>();
    }

    public int getSpecialPoints() {
        return specialPoints;
    }

    public void addSpecialPoints(int points) {
        this.specialPoints += points;
    }

    public void substractSpecialPoints(int points) {
        this.specialPoints -= points;
    }

    public void reset() {
        factoriesNumber = 0;
        shopsNumber = 0;
        population = BigNumber.ZERO();
        apartmentsNumber = 0;
    }

    public String getStateInfo() {
        StringBuilder info = new StringBuilder();
        info
            .append("SpecialPoints: " + specialPoints + "\n")
            .append("Shops: " + shopsNumber + "\n")
            .append("Apartments: " + apartmentsNumber + "\n")
            .append("Population: " + population + "\n")
            .append("Factories: " + factoriesNumber + "\n");
        return info.toString();
    }

    // Gettery i settery dla BigNumber
    public BigNumber getMoney() {
        return money;
    }

    public void addMoney(BigNumber amount) {
        this.money = this.money.add(amount);
    }

    public void addMoney(long amount) {
        this.money = this.money.add(amount);
    }

    public void subtractMoney(BigNumber amount) {
        this.money = this.money.subtract(amount);
    }

    public BigNumber getClicks() {
        return clicks;
    }

    public void addClicks(BigNumber clicks) {
        this.clicks = this.clicks.add(clicks);
    }

    public void addClicks(long clicks) {
        this.clicks = this.clicks.add(clicks);
    }

    public BigNumber getPopulation() {
        return population;
    }

    public void addPopulation(BigNumber population) {
        this.population = this.population.add(population);
    }

    public void addPopulation(long population) {
        this.population = this.population.add(population);
    }

    // Pozostałe metody bez zmian
    public int getFactoriesNumber() { return factoriesNumber; }
    public int getShopsNumber() { return shopsNumber; }
    public int getApartmentsNumber() { return apartmentsNumber; }

    public void addFactory(int value) { this.factoriesNumber += value; }
    public void addShop(int value) { this.shopsNumber += value; }
    public void addApartment(int value) { this.apartmentsNumber += value; }

    public void addUpgrade(Upgrade upgrade) { this.upgrades.add(upgrade); }
    public List<Upgrade> getUpgrades() { return upgrades; }


}
