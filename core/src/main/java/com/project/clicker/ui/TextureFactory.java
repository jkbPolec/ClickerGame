package com.project.clicker.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextureFactory {

    public static TextureRegionDrawable createPlainTextureRegionDrawable(String color) {
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        switch (color) {
            case "RED":
                bgPixmap.setColor(1, 0, 0, 1); // Red
                break;
            case "GREEN":
                bgPixmap.setColor(0, 1, 0, 1); // Green
                break;
            case "BLUE":
                bgPixmap.setColor(0, 0, 1, 1);
                break;
            case "YELLOW":
                bgPixmap.setColor(1, 1, 0, 1); // Yellow
                break;
            case "GREY":
                bgPixmap.setColor(0.5f, 0.5f, 0.5f, 1);
                break;


        }

        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

    }

    public static TextureRegionDrawable createTextureRegionDrawable(String texturePath) {
        Texture texture = new Texture(texturePath);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

}

