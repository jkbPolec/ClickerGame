package com.project.clicker.logic.items;

import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.items.effects.IncreaseClickMoneyEffect;

public class ItemFactory {
    public static Item createItem(ItemData data) {
        IItemEffect effect = null;
        switch (data.effectType) {
            case "IncreaseClickMoney":
                effect = new IncreaseClickMoneyEffect(data.effectValue);
                break;
            case "brak":
                effect = new IncreaseClickMoneyEffect(data.effectValue);
                break;
            // Dodaj kolejne typy efektów tutaj
            default:
                throw new IllegalArgumentException("Nieznany typ efektu: " + data.effectType);
        }
        return new ConcreteItem(
            data.id,
            data.name,
            data.description,
            new BigNumber(data.cost),
            effect,
            data.image
        );
    }
}
