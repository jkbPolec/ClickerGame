package com.project.clicker.logic.Upgrade;

import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeManager;
import com.project.clicker.logic.managers.PopulationManager;
import com.project.clicker.logic.BigNumber;

/**
 * Represents an upgrade that constructs buildings in the game.
 * Buildings can be factories, shops, or apartments, each providing different benefits.
 */
public class BuildingUpgrade extends Upgrade {
    private static final BigNumber APARTMENT_POPULATION_BONUS = new BigNumber(1000);

    private final BuildingUpgradeType buildingType;

    /**
     * Creates a building upgrade with BigNumber cost.
     *
     * @param name the display name of the upgrade
     * @param cost the initial cost of the upgrade
     * @param costIncrease the multiplier for cost increase after each purchase
     * @param gameState the game state to modify
     * @param incomeManager the income manager for income modifications
     * @param populationManager the population manager for population modifications
     * @param buildingType the type of building this upgrade constructs
     */
    public BuildingUpgrade(String name, BigNumber cost, double costIncrease,
                           GameState gameState, IncomeManager incomeManager,
                           PopulationManager populationManager, BuildingUpgradeType buildingType) {
        super(name, cost, costIncrease, gameState, incomeManager, populationManager);
        this.buildingType = buildingType;
    }

    /**
     * Creates a building upgrade with long cost (for compatibility).
     *
     * @param name the display name of the upgrade
     * @param cost the initial cost of the upgrade as a long value
     * @param costIncrease the multiplier for cost increase after each purchase
     * @param gameState the game state to modify
     * @param incomeManager the income manager for income modifications
     * @param populationManager the population manager for population modifications
     * @param buildingType the type of building this upgrade constructs
     */
    public BuildingUpgrade(String name, long cost, double costIncrease,
                           GameState gameState, IncomeManager incomeManager,
                           PopulationManager populationManager, BuildingUpgradeType buildingType) {
        super(name, new BigNumber(cost), costIncrease, gameState, incomeManager, populationManager);
        this.buildingType = buildingType;
    }

    /**
     * Gets the type of building this upgrade constructs.
     *
     * @return the building type
     */
    public BuildingUpgradeType getBuildingType() {
        return buildingType;
    }

    @Override
    public UpgradeInfo getUpgradeInfo() {
        return UpgradeInfo.fromBuilding(name, cost, buildingType, timesActivated);
    }

    @Override
    public void apply() {
        if (!canAfford()) {
            return;
        }

        // Process the purchase
        active = true;
        timesActivated++;
        gameState.subtractMoney(cost);
        increaseCost();

        // Apply building-specific effects
        applyBuildingEffect();
    }

    /**
     * Applies the specific effect based on the building type.
     */
    private void applyBuildingEffect() {
        switch (buildingType) {
            case FACTORY:
                gameState.addFactory(1);
                break;

            case SHOP:
                gameState.addShop(1);
                break;

            case APARTMENT:
                gameState.addApartment(1);
                gameState.addPopulation(APARTMENT_POPULATION_BONUS);
                break;

            default:
                throw new IllegalStateException("Unknown building type: " + buildingType);
        }
    }

}
