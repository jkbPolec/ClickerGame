package com.project.clicker.logic.items;

import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeManager;

public abstract class Item {
    protected int itemId;
    protected String name;
    protected String description;
    protected int cost;
    protected IItemEffect effect;
    protected String image;
    private boolean isBought = false;


    public Item(int id,String name, String description, int cost, IItemEffect effect, String image) {
        this.itemId = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.effect = effect;
        this.image = image;
    }

    public void use(IncomeManager incomeManager) {
        effect.apply(incomeManager);
    }

    public String getDescription() {
        return "Cost: " + cost + " SP" + "\n" + name + "\n" + effect.getDescription() + "\n";
    }

    public String getAllData() {
        return "Item ID: " + itemId + "\n" +
               "Name: " + name + "\n" +
               "Description: " + description + "\n" +
               "Cost: " + cost + "\n" +
               "Effect: " + effect.getDescription() + "\n" +
               "Image: " + image;
    }

    public boolean isBought() {
        return isBought;
    }

    public String getImage() {
        return image;
    }

    public boolean buy(GameState state, IncomeManager incomeManager) {
        if (isBought) {
            return false; // Item already bought
        }
        if (state.getSpecialPoints() <= cost) {
            return false; // Not enough money
        }
        state.subtractSpecialPoints(cost);
        isBought = true;
        use(incomeManager); // Apply the effect after buying
        return true;
    }

}
