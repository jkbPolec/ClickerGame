package com.project.clicker.logic.managers;

import com.project.clicker.MainScene;
import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.Upgrade.Upgrade;

import javax.rmi.ssl.SslRMIClientSocketFactory;

public class ResetManager {
    public GameState state;
    public IncomeManager incomeManager;
    public IncomeMultipliers incomeMultipliers;

    public ResetManager(GameState state, IncomeManager incomeManager) {
        this.state = state;
        this.incomeManager = incomeManager;
        this.incomeMultipliers = incomeManager.getMultipliers();
    }

    public void reset() {
        incomeMultipliers.resetMultipliers();
        state.reset();
        incomeManager.resetIncome();
        for (Upgrade upgrade : state.getUpgrades()) {
            upgrade.reset();
        }

    }

    public void buySpecialPoints(int timesUsed) {
        BigNumber cost = new BigNumber(1000000);
        cost.multiply(new BigNumber(timesUsed));

        if (state.getMoney().isGreaterOrEqual(cost)) {
            state.subtractMoney(cost.multiply(timesUsed));
            state.addSpecialPoints(timesUsed);
            reset();
        }

    }


}
