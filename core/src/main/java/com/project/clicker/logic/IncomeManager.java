package com.project.clicker.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class IncomeManager {
    private final GameState state;
    private final IncomeMultipliers multipliers = new IncomeMultipliers();
    private BigNumber basicPassiveIncome = new BigNumber(10);
    private BigNumber basicPerClickIncome = BigNumber.ONE();
    private BigNumber factoryIncome = BigNumber.ZERO();

    private float timer = 0f;

    public IncomeManager(GameState state) {
        this.state = state;
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
        state.addClicks(1);
    }

    public void increasePassiveIncomeMultiplier(double value) {
        multipliers.passive += value;
    }

    public void increaseMoneyPerClickMultiplier(double value) {
        multipliers.perClick += value;
    }

    public void increaseFactoryIncome(BigNumber value) {
        factoryIncome = factoryIncome.add(value);
    }

    public void increaseFactoryIncome(long value) {
        factoryIncome = factoryIncome.add(value);
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
        BigNumber factoryContribution = factoryIncome.multiply(multipliers.factory);
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
