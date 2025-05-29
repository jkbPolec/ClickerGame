package com.project.clicker.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinManager {

    private static SkinManager instance;
    public Skin skin;

    private SkinManager() {
            skin = new Skin(Gdx.files.internal("uiskin.json"));
        }

    public static SkinManager getInstance() {
        if (instance == null) {
            instance = new SkinManager();
        }

        return instance;
    }
}







