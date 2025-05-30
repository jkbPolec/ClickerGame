package com.project.clicker.logic.items;

import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;

public abstract class Item {

    protected String name;
    protected String description;
    protected BigNumber cost;
    protected IItemEffect effect;
    protected String image;

    public Item(String name, String description, BigNumber cost, IItemEffect effect, String image) {
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
        return "Name: " + name + "\n" +
               "Description: " + description + "\n" +
               "Cost: " + cost.toString() + "\n" +
               "Effect: " + effect.getDescription() + "\n" +
               "Image: " + image;
    }

}
