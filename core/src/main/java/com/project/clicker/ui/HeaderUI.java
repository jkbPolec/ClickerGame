package com.project.clicker.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HeaderUI extends Table {
    public HeaderUI(Skin skin, ClickListener exitListener, ClickListener soundListener,  ClickListener shopListener) {
        // Styl przycisków bez tła i z niestandardową czcionką
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = null;
        style.down = null;
        style.over = null;
        style.disabled = null;

        // Własna bitmapowa czcionka
        BitmapFont customFont = new BitmapFont(Gdx.files.internal("pixel_font_header.fnt"));
        style.font = customFont;
        style.fontColor = Color.WHITE;

        // Utworzenie przycisków
        TextButton exitButton = new TextButton("Exit", style);
        exitButton.addListener(exitListener);

        TextButton soundButton = new TextButton("Sound", style);
        soundButton.addListener(soundListener);

        TextButton shopButton = new TextButton("Shop", style);
        shopButton.addListener(shopListener);

        // Układ w Table – poziomo, z dużym odstępem między przyciskami
        this.top().padTop(10); // wyrównanie do góry + górny margines
        this.add(exitButton).padRight(100); // odstęp między przyciskami
        this.add(soundButton).padRight(100);
        this.add(shopButton);
    }
}
