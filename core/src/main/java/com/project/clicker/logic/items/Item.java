package com.project.clicker.logic.items;

import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;

public abstract class Item {


    protected int itemId;
    protected String name;
    protected String description;
    protected BigNumber cost;
    protected IItemEffect effect;
    protected String image;
    private boolean isBought = false;


    public Item(int id,String name, String description, BigNumber cost, IItemEffect effect, String image) {
        this.itemId = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.effect = effect;
        this.image = image;
    }

    public void use(GameState state) {
        effect.apply(state);
    }

    public String getDescription() {
        return description + " " + effect.getDescription();
    }

    public String getAllData() {
        return "Item ID: " + itemId + "\n" +
               "Name: " + name + "\n" +
               "Description: " + description + "\n" +
               "Cost: " + cost.toString() + "\n" +
               "Effect: " + effect.getDescription() + "\n" +
               "Image: " + image;
    }

    public boolean isBought() {
        return isBought;
    }

    public String getImage() {
        return image;
    }

    public boolean buy(GameState state) {
        if (isBought) {
            return false; // Item already bought
        }
        if (state.getMoney().isLessThan(cost)) {
            return false; // Not enough money
        }
        state.subtractMoney(cost);
        isBought = true;
        use(state); // Apply the effect after buying
        return true;
    }

}
