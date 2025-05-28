package com.project.clicker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.project.clicker.ui.TableFactory;
import com.project.clicker.ui.TextureFactory;

public class MainScene implements Screen {
    private Stage stage;
    private Skin skin;
    private int clicks = 0;
    private Label clickLabel;


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        clickLabel = new Label("Kliknięcia: 0", skin);

        TextButton clickButton = new TextButton("Klik!", skin);
        clickButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event,  float x, float y){
                clicks ++;
                clickLabel.setText("Kliknięcia: " + clicks);
            }


        });


        TableFactory tableFactory = new TableFactory(skin);
        TextureFactory textureFactory = new TextureFactory();

        Table mainTable = new Table();
        //mainTable.setFillParent(true);
        //mainTable.top().left();
        mainTable.setSize(1920,1080);
        Table menuTable = tableFactory.createScrollableMenu("Menu", "Start", "Ustawienia", "Wyjście", "Pomoc",
            "O grze", "Statystyki", "O autorze", "Kontakt", "Wersja", "Informacje", "Zgłoś błąd", "Zamknij grę");
        Table clickerTable = new Table();
        clickerTable.add(clickLabel).row();
        clickerTable.add(clickButton);
        //clickerTable.setFillParent(true);
        clickerTable.setBackground(textureFactory.createPlainTextureRegionDrawable("BLUE"));

        mainTable.add(menuTable).expand().left().padTop(50).padLeft(50).size(440, 1000);
        mainTable.add(clickerTable).expand().size(100,100);

        mainTable.setBackground(textureFactory.createPlainTextureRegionDrawable("RED"));
        stage.addActor(mainTable);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
