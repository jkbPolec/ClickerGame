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
import org.w3c.dom.Text;

import java.util.List;
import java.util.ArrayList;

public class Hud {
    private Stage stage;
    private Label moneyLabel;
    private Label clickLabel;
    private Label passiveIncomeLabel;
    private GameState state;
    private IncomeManager incomeManager;
    private UpgradeFactory upgradeFactory;

    private List<TextButton> upgradeButtons = new ArrayList<>();

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
        for (Upgrade upgrade : state.getUpgrades()) {
            String text = upgrade.getUpgradeInfo();
            TextButton upgradeButton = new TextButton(text, skin);
            upgradeButton.setDisabled(false);
            upgradeButton.setTouchable(Touchable.enabled);

            upgradeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    upgrade.apply();
                }
            });

            upgradeButtons.add(upgradeButton);        // dodaj do listy
            table.add(upgradeButton).padTop(5).row(); // dodaj do UI
        }

        moneyLabel = new Label("Money: 0", skin);


        moneyLabel = new Label("Money: 0", skin);
        passiveIncomeLabel = new Label("Pas. przyrost: " + incomeManager.getPassiveIncome(), skin);
        table.bottom().left();
        table.add(clickLabel).padBottom(20).row();
        table.add(clickButton).row();
        table.add(moneyLabel).padLeft(60f).padBottom(10f).row();
        table.add(passiveIncomeLabel).padLeft(60f).padBottom(40f).row();
    }

    public Stage getStage() {
        return stage;
    }

    public void update() {
        moneyLabel.setText("Money: " + state.getMoney());
        passiveIncomeLabel.setText("Pas. przyrost: " + incomeManager.getPassiveIncome());
        for (int i = 0; i < state.getUpgrades().size(); i++) {
            Upgrade upgrade = state.getUpgrades().get(i);
            TextButton button = upgradeButtons.get(i);
            button.setText(upgrade.getUpgradeInfo());

            if (upgrade.getCost() <= state.getMoney()) {
                button.setDisabled(false);
                button.setTouchable(Touchable.enabled);
            } else {
                button.setDisabled(true);
                button.setTouchable(Touchable.disabled);
            }
        }
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

