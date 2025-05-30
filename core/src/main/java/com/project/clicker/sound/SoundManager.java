package com.project.clicker.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class SoundManager {
    private static SoundManager instance;
    private final HashMap<String, Sound> sounds = new HashMap<>();

    private final List<Sound> clickSounds = new ArrayList<>();

    private boolean muted = false;

    private SoundManager() {}

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public static SoundManager getInstance() {
        if (instance == null) instance = new SoundManager();
        return instance;
    }

    public void load(String name, String file) {
        sounds.put(name, Gdx.audio.newSound(Gdx.files.internal(file)));
    }

    public void loadClickSound(String file) {
        clickSounds.add(Gdx.audio.newSound(Gdx.files.internal(file)));
    }

    public void play(String name) {
        if (muted) return;
        Sound sound = sounds.get(name);
        if (sound != null) sound.play();
    }

    public void playRandomClick() {
        if (muted) return;
        if (!clickSounds.isEmpty()) {
            clickSounds.get(random.nextInt(clickSounds.size())).play();
        }
    }

    public void dispose() {
        for (Sound sound : sounds.values()) sound.dispose();
        sounds.clear();
    }
}
