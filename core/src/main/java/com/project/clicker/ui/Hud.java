package com.project.clicker.ui;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.project.clicker.UpgradeFactory;
import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.clicker.logic.managers.IncomeManager;
import com.project.clicker.logic.managers.ResetManager;
import com.project.clicker.logic.items.ItemLoader;
import com.project.clicker.sound.SoundManager;

public class Hud {
    private final Stage stage;
    private final GameState state;
    private final IncomeManager incomeManager;
    BigNumber money;
    private Texture backgroundTexture;

    private final ClickingUI clickingUI;
    private final UpgradesUI upgradesUI;
    private ShopUI shopUI;
    private InfoUI multipliersUI;
    private SPButtonUI spButtonUI; // Nowa klasa dla przycisków Special Points

    public Hud(Viewport viewport, Skin skin, GameState state, IncomeManager incomeManager, UpgradeFactory upgradeFactory, ItemLoader itemLoader, ResetManager resetManager) {
        this.stage = new Stage(viewport);
        this.state = state;
        this.incomeManager = incomeManager;

        this.backgroundTexture = new Texture(Gdx.files.internal("City-Backgrounds-Pixel-Art.png"));
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));

        shopUI = new ShopUI(skin, itemLoader.getItems(), state, incomeManager);
        shopUI.setFillParent(true);
        shopUI.center();
        shopUI.setVisible(false);
        shopUI.setTouchable(Touchable.enabled);

        clickingUI = new ClickingUI(skin, state, incomeManager);
        upgradesUI = new UpgradesUI(skin, state);
        multipliersUI = new InfoUI(skin, incomeManager.getMultipliers(), state);
        spButtonUI = new SPButtonUI(skin, resetManager, state); // Utworzenie UI dla przycisków SP

        Table mainTable = new Table();
        mainTable.setSize(1920, 1080);

        // Header pozostaje bez zmian
        HeaderUI header = new HeaderUI(
            skin,
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.exit();
                }
            },
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SoundManager sm = SoundManager.getInstance();
                    sm.setMuted(!sm.isMuted());
                }
            },
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    shopUI.setVisible(true);
                }
            }
        );

        Texture headerTexture = new Texture(Gdx.files.internal("header.png"));
        headerTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegionDrawable headerBackground = new TextureRegionDrawable(new TextureRegion(headerTexture));
        header.setBackground(headerBackground);

        mainTable.top();
        // Header zajmuje całą górną część (2 kolumny)
        mainTable.add(header).colspan(3).expandX().fillX().row();

        // Tabela z 3 kolumnami pod headerem
        // Kolumna 1: UpgradesUI (stały rozmiar 650x1000)
        mainTable.add(upgradesUI.getContainer())
            .left()
            .padTop(10)
            .padLeft(10)
            .size(650, 1000);

        // Kolumna 2: Przyciski SP i label statystyk (środkowa kolumna)
        Table statsTable = new Table();

        // Dodanie przycisków Special Points na górze
        statsTable.add(spButtonUI.getContainer())
            .expandX()
            .fillX()
            .pad(10)
            .top()
            .row();

        // Pusta przestrzeń która się rozciąga
        statsTable.add().expand().row();

        // Dodanie labela statystyk na dole
        statsTable.add(multipliersUI.getLabel())
            .expandX()
            .fillX()
            .pad(20)
            .bottom();

        mainTable.add(statsTable)
            .expandX()
            .expandY()
            .fillX()
            .fillY()
            .padTop(10);

        // Kolumna 3: ClickingUI (zachowuje obecny rozmiar)
        mainTable.add(clickingUI.getTable())
            .right()
            .padTop(10)
            .padRight(10)
            .size(600);

        mainTable.setBackground(backgroundDrawable);
        stage.addActor(mainTable);
        stage.addActor(shopUI);
    }

    public Stage getStage() {
        return stage;
    }

    public void update() {
        clickingUI.update(state, incomeManager);
        upgradesUI.update(state);
        multipliersUI.update(incomeManager.getMultipliers(), state);
        spButtonUI.update(); // Aktualizacja przycisków Special Points
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1); // czarne tło
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }

    public interface UpdateCallback {
        void onButtonClicked();
    }
}
