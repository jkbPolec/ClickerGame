package com.project.clicker.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HeaderUI extends Table {
    public HeaderUI(Skin skin, ClickListener exitListener, ClickListener soundListener,  ClickListener shopListener) {
        TextButton exitButton = new TextButton("Wyjście", skin);
        exitButton.addListener(exitListener);

        TextButton soundButton = new TextButton("Dźwięk", skin);
        soundButton.addListener(soundListener);

        TextButton shopButton = new TextButton("Sklep", skin);
        shopButton.addListener(shopListener);

        this.add(exitButton).pad(10);
        this.add(soundButton).pad(10);
        this.add(shopButton).pad(10);
        this.top();
    }
}
