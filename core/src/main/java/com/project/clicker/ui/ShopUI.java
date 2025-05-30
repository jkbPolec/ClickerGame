package com.project.clicker.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private final Window descWindow;

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

        // Okienko opisu (na początku niewidoczne)
        descWindow = new Window("", skin);
        descWindow.setVisible(false);
        descWindow.setMovable(false);
        descWindow.setKeepWithinStage(true);
        this.addActor(descWindow);


        int col = 0;
        for (Item item : items) {
            Texture texture = new Texture(Gdx.files.internal("items/"+ item.getImage()));
            Image image = new Image(new TextureRegion(texture));


            ImageButton button = new ImageButton(skin);
            button.add(image).size(140, 140);


            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println(item);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    // Ustaw tekst i pozycję okienka opisu
                    descWindow.clear();
                    descWindow.add(new Label(item.getDescription(), skin)).pad(10);
                    descWindow.pack();
                    descWindow.toFront();

                    // Pozycja pod przyciskiem
                    Vector2 pos = button.localToStageCoordinates(new Vector2(0, 0));
                    descWindow.setPosition(pos.x, pos.y - descWindow.getHeight() - 10);
                    descWindow.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    descWindow.setVisible(false);
                }

            });

            this.add(button).size(150, 150).pad(10);

            col++;
            if (col % 4 == 0) this.row();
        }
        if (col % 4 != 0) this.row();
    }

}
