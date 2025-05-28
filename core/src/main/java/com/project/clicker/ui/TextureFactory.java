package com.project.clicker.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextureFactory {

    public TextureRegionDrawable createPlainTextureRegionDrawable(String color) {
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


        }

        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

    }

}

