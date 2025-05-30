package com.project.clicker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.project.clicker.logic.*;
import com.project.clicker.logic.items.ItemLoader;
import com.project.clicker.ui.Hud;

public class MainScene implements Screen {
    private Main game;
    private GameState state;
    private IncomeManager incomeManager;
    private PopulationManager populationManager;
    private Hud hud;
    private UpgradeFactory upgradeFactory;
    private ItemLoader itemLoader;

    public MainScene(Main game) {
        this.game = game;
        this.state = new GameState();
        this.incomeManager = new IncomeManager(state);
        this.populationManager = new PopulationManager(state);
        this.upgradeFactory = new UpgradeFactory(state, incomeManager, populationManager);
        this.upgradeFactory.initializeUpgrades();
        this.itemLoader = new ItemLoader("items.json");
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        hud = new Hud(new ScreenViewport(), skin, state, incomeManager, upgradeFactory, itemLoader);
        Gdx.input.setInputProcessor(hud.getStage());
    }

    @Override
    public void render(float delta) {
        incomeManager.update(delta);
        hud.update();
        hud.render(); // act + draw
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        hud.dispose();
    }
}
