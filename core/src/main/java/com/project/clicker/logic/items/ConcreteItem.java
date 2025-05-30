package com.project.clicker.logic.items;

import com.project.clicker.logic.BigNumber;

public class ConcreteItem extends Item {
    public ConcreteItem(String name, String description, BigNumber cost, IItemEffect effect, String image) {
        super(name, description, cost, effect, image);
    }

}
