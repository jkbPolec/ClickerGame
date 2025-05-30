package com.project.clicker.logic.items;

import com.project.clicker.logic.GameState;

public interface IItemEffect {
    void apply(GameState state);
    String getDescription();
}
