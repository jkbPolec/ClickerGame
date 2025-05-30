package com.project.clicker;

import com.badlogic.gdx.Game;
import com.project.clicker.sound.SoundManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {

        LoadSounds();

        setScreen(new MainScene(this));
    }

    private void LoadSounds() {
        SoundManager sm = SoundManager.getInstance();
        sm.loadClickSound("sounds/dollarClickSound1.wav");
        sm.loadClickSound("sounds/dollarClickSound2.wav");
        sm.loadClickSound("sounds/dollarClickSound3.wav");
    }
}
