package com.project.clicker.logic.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;

public class IncomeManager {
    private final GameState state;
    private final IncomeMultipliers multipliers = new IncomeMultipliers();
    private BigNumber basicPassiveIncome = new BigNumber(10);
    private BigNumber basicPerClickIncome = BigNumber.ONE();
    private BigNumber basicShopsIncome = BigNumber.ONE();
    private BigNumber basicFactoryIncome = BigNumber.ONE();

    private float timer = 0f;

    public IncomeManager(GameState state) {
        this.state = state;
    }

    public IncomeMultipliers getMultipliers() {
        return multipliers;
    }

    public void resetIncome() {
        basicPassiveIncome = new BigNumber(10);
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= 1f) {
            int ticks = (int)(timer);
            BigNumber passiveIncome = calculatePassiveIncome();
            BigNumber totalIncome = passiveIncome.multiply(ticks);
            state.addMoney(totalIncome);
            timer -= ticks;
        }

        // Debugging: Press SPACE to add money
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            state.addMoney(new BigNumber(100000));
        }
    }

    public void addMoneyFromClick() {
        BigNumber clickIncome = calculateMoneyPerClick();
        state.addMoney(clickIncome);
    }

    public void multiplyBasicIncomeFromShops(double value) {basicShopsIncome.multiply(value);}

    public void multiplyBasicMoneyPerClick(double value) {basicPerClickIncome.multiply(value);}

    public void multiplyBasicFactoryIncome(double value) {basicFactoryIncome.multiply(value);}

    public void increasePassiveIncomeMultiplier(double value) {
        multipliers.passive += value;
    }

    public void increaseMoneyPerClickMultiplier(double value) {
        multipliers.perClick += value;
    }

    public void increaseFactoryIncomeMultiplier(double value) {
        multipliers.factory += value;
    }

    public void increaseIncomeFromShopsMultiplier(double value) {
        multipliers.shops *= value;
    }

    public BigNumber getPassiveIncome() {
        return calculatePassiveIncome();
    }

    private BigNumber calculatePassiveIncome() {
        BigNumber value = new BigNumber(basicPassiveIncome);

        // Dochód z fabryk
        BigNumber factoryContribution = basicFactoryIncome.multiply(state.getFactoriesNumber()).multiply(multipliers.factory);
        value = value.add(factoryContribution);

        // Dochód ze sklepów (populacja * liczba sklepów * mnożnik)
        BigNumber shopContribution = state.getPopulation()
                .multiply(state.getShopsNumber())
                .multiply(multipliers.shops);
        value = value.add(shopContribution);

        // Zastosuj mnożnik pasywny
        value = value.multiply(multipliers.passive);

        return value;
    }

    private BigNumber calculateMoneyPerClick() {
        return basicPerClickIncome.multiply(multipliers.perClick);
    }
}
