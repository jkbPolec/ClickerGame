package com.project.clicker.logic.items;

import com.project.clicker.logic.BigNumber;

public class ConcreteItem extends Item {
    public ConcreteItem(int id ,String name, String description, BigNumber cost, IItemEffect effect, String image) {
        super(id, name, description, cost, effect, image);
    }

}
