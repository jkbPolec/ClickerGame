package com.project.clicker.logic.items;

import com.project.clicker.logic.BigNumber;

public class ItemFactory {
    public static Item createItem(ItemData data) {
        IItemEffect effect = null;
        switch (data.effectType) {
            case "IncreaseClickMoney":
                effect = new IncreaseClickMoneyEffect(data.effectValue);
                break;
            // Dodaj kolejne typy efektów tutaj
            default:
                throw new IllegalArgumentException("Nieznany typ efektu: " + data.effectType);
        }
        return new ConcreteItem(
            data.name,
            data.description,
            new BigNumber(data.cost),
            effect,
            data.image
        );
    }
}
