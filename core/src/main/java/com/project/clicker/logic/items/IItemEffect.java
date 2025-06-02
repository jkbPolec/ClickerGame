package com.project.clicker.logic.items;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeManager;

public interface IItemEffect {
    void apply(IncomeManager incomeManager);
    String getDescription();
}
