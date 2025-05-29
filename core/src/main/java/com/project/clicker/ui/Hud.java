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

    private List<Label> upgradeLabels = new ArrayList<>();
    private List<ImageTextButton> upgradeButtons = new ArrayList<>();

    public Hud(Viewport viewport, Skin skin, GameState state, IncomeManager incomeManager, UpgradeFactory upgradeFactory) {
        this.stage = new Stage(viewport);
        this.state = state;
        this.incomeManager = incomeManager;
        this.upgradeFactory = upgradeFactory;

        Table mainTable = new Table();
        mainTable.setSize(1920, 1080);

        //UI DO KLIKANIA

        Table clickingTable = new Table();
        clickLabel = new Label("Kliknięcia: 0", skin);

        ImageButton imageButton = new ImageButton(skin, "dollar-button");
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                state.addClicks(1);
                incomeManager.addMoneyFromClick();
                clickLabel.setText("Kliknięcia: " + state.getClicks());
            }


        });

        clickingTable.add(clickLabel).row();
        clickingTable.add(imageButton).size(300, 300).row();


        //UI DO ULEPSZEN
        // Nowy przycisk do ulepszeń
        Table upgradeTable = new Table();

        for (Upgrade upgrade : state.getUpgrades()) {
            Table buttonTable = new Table();
            String text = upgrade.getUpgradeInfo();
            ImageTextButton upgradeButton = new ImageTextButton(".", skin, "upgrade-button");
            upgradeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    upgrade.apply();
                }
            });
            Label description = new Label(upgrade.getUpgradeInfo(), skin, "upgrade-description"); // Przykładowy opis

            upgradeButton.setDisabled(false);
            upgradeButton.setTouchable(Touchable.enabled);

            upgradeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    upgrade.apply();
                }
            });

            upgradeLabels.add(description);        // dodaj do listy
            upgradeButtons.add(upgradeButton);    // dodaj do listy
            buttonTable.add(upgradeButton).expand().left();
            buttonTable.add(description).expand().right();
            buttonTable.setBackground(skin.getDrawable("button"));
            upgradeTable.add(buttonTable).size(600,150).padBottom(50).row(); // dodaj do UI
        }

        ScrollPane scrollPane = new ScrollPane(upgradeTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(false, false); // pozwala przewijać pionowo i poziomo (możesz ustawić na true jeśli chcesz tylko pion)

        Table container = new Table();
        //container.setFillParent(true);
        container.top().padTop(50);
        container.add(scrollPane).grow(); // scrollPane zajmie całą dostępną przestrzeń

        container.setSize(100, 100);

        //SKLADANIE CALOSCI
        moneyLabel = new Label("Money: 0", skin);



        passiveIncomeLabel = new Label("Pas. przyrost: " + incomeManager.getPassiveIncome(), skin);


        clickingTable.add(moneyLabel).row();
        clickingTable.add(passiveIncomeLabel).row();
        mainTable.add(container).expand().left().padTop(50).padLeft(50).size(640, 1000);
        mainTable.add(clickingTable).expand().expand();

        mainTable.setBackground(TextureFactory.createPlainTextureRegionDrawable("RED"));
        stage.addActor(mainTable);
    }

    public Stage getStage() {
        return stage;
    }

    public void update() {
        moneyLabel.setText("Money: " + state.getMoney());
        passiveIncomeLabel.setText("Pas. przyrost: " + incomeManager.getPassiveIncome());
        for (int i = 0; i < state.getUpgrades().size(); i++) {
            Upgrade upgrade = state.getUpgrades().get(i);
            upgradeLabels.get(i).setText(upgrade.getUpgradeInfo());
            ImageTextButton button = upgradeButtons.get(i);
            button.setText("Przycisk");

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

