package com.project.clicker.ui;

import com.badlogic.gdx.graphics.GL20;
import com.project.clicker.logic.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
    private Stage stage;
    private Label moneyLabel;
    private GameState state;

    public Hud(Viewport viewport, Skin skin, GameState state) {
        this.stage = new Stage(viewport);
        this.state = state;

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        moneyLabel = new Label("Money: 0", skin);
        table.add(moneyLabel).expandX().padTop(10f);

        stage.addActor(table);
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
