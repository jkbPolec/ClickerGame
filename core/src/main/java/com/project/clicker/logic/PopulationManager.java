package com.project.clicker.logic;

public class PopulationManager {
    private GameState state;

    public PopulationManager(GameState state) {
        this.state = state;
    }

    public void increasePopulation(int people) {
        state.addPopulation(people);
    }


}
