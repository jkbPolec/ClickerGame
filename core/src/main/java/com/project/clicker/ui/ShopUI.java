package com.project.clicker.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.project.clicker.logic.items.Item;
import com.project.clicker.ui.TextureFactory;


import java.util.List;

public class ShopUI extends Table {
    public ShopUI(Skin skin, List<Item> items) {
        this.setBackground(TextureFactory.createPlainTextureRegionDrawable("BLUE"));
        this.setVisible(false);

        TextButton closeButton = new TextButton("Zamknij", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        this.add(closeButton).pad(10).colspan(4).row();

        Label title = new Label("Sklep", skin);
        this.add(title).padBottom(20).colspan(4).row();

        int col = 0;
        for (Item item : items) {
            Texture texture = new Texture(Gdx.files.internal("items/"+ item.getImage()));
            Image image = new Image(new TextureRegion(texture));
            image.setScaling(Scaling.stretch);
            image.setSize(150, 150);



            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.up = skin.newDrawable("white", 0f, 0f, 0f, 0f); // przezroczyste tło
            ImageButton button = new ImageButton(style);
            button.add(image).size(150, 150);


            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println(item);
                }
            });

            this.add(button).size(150, 150).pad(10);

            col++;
            if (col % 4 == 0) this.row();
        }
        if (col % 4 != 0) this.row();
    }
}
