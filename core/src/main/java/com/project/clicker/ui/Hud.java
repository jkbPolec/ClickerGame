package com.project.clicker.ui;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.project.clicker.UpgradeFactory;
import com.project.clicker.logic.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.clicker.logic.IncomeManager;
import com.project.clicker.logic.Upgrade.Upgrade;

import java.util.List;
import java.util.ArrayList;

public class Hud {
    private final Stage stage;
    private Label moneyLabel;
    private Label clickLabel;
    private Label passiveIncomeLabel;
    private final GameState state;
    private final IncomeManager incomeManager;
    private final UpgradeFactory upgradeFactory;

    private final ClickingUI clickingUI;
    private final UpgradesUI upgradesUI;

    public Hud(Viewport viewport, Skin skin, GameState state, IncomeManager incomeManager, UpgradeFactory upgradeFactory) {
        this.stage = new Stage(viewport);
        this.state = state;
        this.incomeManager = incomeManager;
        this.upgradeFactory = upgradeFactory;

        clickingUI = new ClickingUI(skin, state, incomeManager);
        upgradesUI = new UpgradesUI(skin, state);

        Table mainTable = new Table();
        mainTable.setSize(1920, 1080);

        mainTable.add(upgradesUI.getContainer()).expand().left().padTop(50).padLeft(50).size(640, 1000);
        mainTable.add(clickingUI.getTable()).expand().expand();

        mainTable.setBackground(TextureFactory.createPlainTextureRegionDrawable("RED"));
        stage.addActor(mainTable);
    }


    public Stage getStage() {
        return stage;
    }

    public void update() {
        clickingUI.update(state, incomeManager);
        upgradesUI.update(state);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1); // czarne tło
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}

