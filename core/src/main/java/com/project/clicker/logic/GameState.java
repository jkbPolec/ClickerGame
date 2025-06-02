package com.project.clicker.logic;

import com.project.clicker.logic.Upgrade.Upgrade;
import com.project.clicker.logic.managers.IncomeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the current state of the game, including resources, upgrades, and building counts.
 */
public class GameState {
    private BigNumber money;
    private BigNumber clicks;
    private BigNumber population;

    private int factoriesNumber;
    private int shopsNumber;
    private int apartmentsNumber;
    private int specialPoints;

    private final List<Upgrade> upgrades;

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

    // === Resource Management ===

    public BigNumber getMoney() {
        return money;
    }

    public void addMoney(BigNumber amount) {
        this.money = this.money.add(amount);
    }

    public void addMoney(long amount) {
        this.addMoney(new BigNumber(amount));
    }

    public void subtractMoney(BigNumber amount) {
        this.money = this.money.subtract(amount);
    }

    public BigNumber getClicks() {
        return clicks;
    }

    public void addClicks(BigNumber amount) {
        this.clicks = this.clicks.add(amount);
    }

    public void addClicks(long amount) {
        this.addClicks(new BigNumber(amount));
    }

    public BigNumber getPopulation() {
        return population;
    }

    public void addPopulation(BigNumber amount) {
        this.population = this.population.add(amount);
    }

    public void addPopulation(long amount) {
        this.addPopulation(new BigNumber(amount));
    }

    // === Special Points ===

    public int getSpecialPoints() {
        return specialPoints;
    }

    public void addSpecialPoints(int points) {
        this.specialPoints += points;
    }

    public void subtractSpecialPoints(int points) {
        this.specialPoints -= points;
    }

    // === Buildings ===

    public int getFactoriesNumber() {
        return factoriesNumber;
    }

    public void addFactory(int count) {
        this.factoriesNumber += count;
    }

    public int getShopsNumber() {
        return shopsNumber;
    }

    public void addShop(int count) {
        this.shopsNumber += count;
    }

    public int getApartmentsNumber() {
        return apartmentsNumber;
    }

    public void addApartment(int count) {
        this.apartmentsNumber += count;
    }

    // === Upgrades ===

    public void addUpgrade(Upgrade upgrade) {
        this.upgrades.add(upgrade);
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    // === Utility ===

    /**
     * Resets key parts of the game state to their initial values (excluding money/clicks).
     */
    public void reset() {
        this.factoriesNumber = 0;
        this.shopsNumber = 0;
        this.apartmentsNumber = 0;
        this.population = BigNumber.ZERO();
    }

    /**
     * Returns a string summarizing key game statistics.
     */
    public String getStateInfo() {
        return "SpecialPoints: " + specialPoints + "\n" +
            "Shops: " + shopsNumber + "\n" +
            "Apartments: " + apartmentsNumber + "\n" +
            "Population: " + population + "\n" +
            "Factories: " + factoriesNumber + "\n";
    }
}
