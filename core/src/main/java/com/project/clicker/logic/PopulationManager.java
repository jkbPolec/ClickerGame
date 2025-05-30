package com.project.clicker.logic;

public class PopulationManager {
    private GameState state;

    public PopulationManager(GameState state) {
        this.state = state;
    }

    public void increasePopulation(long people) {
        state.addPopulation(people);
    }


}
