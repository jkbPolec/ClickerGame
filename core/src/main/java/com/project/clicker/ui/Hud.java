package com.project.clicker.ui;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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

public class Hud {
    private Stage stage;
    private Label moneyLabel;
    private Label clickLabel;
    private GameState state;
    private IncomeManager incomeManager;
    private UpgradeFactory upgradeFactory;

    public Hud(Viewport viewport, Skin skin, GameState state, IncomeManager incomeManager, UpgradeFactory upgradeFactory) {
        this.stage = new Stage(viewport);
        this.state = state;
        this.incomeManager = incomeManager;
        this.upgradeFactory = upgradeFactory;

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        clickLabel = new Label("Kliknięcia: 0", skin);

        TextButton clickButton = new TextButton("Klik!", skin);
        clickButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                state.addClicks(1);
                incomeManager.addMoneyFromClick();
                clickLabel.setText("Kliknięcia: " + state.getClicks());
            }
        });

        // Nowy przycisk do ulepszeń
        TextButton upgradeButton = new TextButton("Ulepsz", skin);
        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                state.getUpgrades().stream()
                    .filter(u -> u.getName().equals("Passive Income Boost"))
                    .findFirst()
                    .ifPresent(upgradeFactory::applyUpgrade);
            }
        });

        moneyLabel = new Label("Money: 0", skin);
        table.bottom().left();
        table.add(clickLabel).padBottom(20).row();
        table.add(clickButton).row();
        table.add(upgradeButton).padTop(10).row(); // dodanie przycisku
        table.add(moneyLabel).padLeft(60f).padBottom(40f).row();
    }

    public Stage getStage() {
        return stage;
    }

    public void update() {
        moneyLabel.setText("Money: " + state.getMoney());
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

