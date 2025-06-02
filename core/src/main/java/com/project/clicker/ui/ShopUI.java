package com.project.clicker.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.items.Item;
import com.project.clicker.logic.managers.IncomeManager;


import java.util.List;

public class ShopUI extends Table {
    private final Window descWindow;

    public ShopUI(Skin skin, List<Item> items, GameState state, IncomeManager incomeManager) {
        // Ustaw tło z pliku background.png
        Texture backgroundTexture = new Texture(Gdx.files.internal("City-Backgrounds-Pixel-Art.png"));
        this.setBackground(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        this.setVisible(false);

        // Załaduj czcionkę pixel
        BitmapFont pixelFont = new BitmapFont(Gdx.files.internal("pixel_font_clickingUI.fnt"));

        // Skopiuj istniejący skin i dodaj pixel font
        skin.add("pixel-font", pixelFont, BitmapFont.class);

        // Stwórz nowe style z pixel fontem
        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
        labelStyle.font = pixelFont;

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = pixelFont;
        buttonStyle.up = null;
        buttonStyle.down = null;
        buttonStyle.over = null;

        TextButton closeButton = new TextButton("Exit", buttonStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        this.add(closeButton).pad(10).colspan(4).row();

        Label title = new Label("Shop", labelStyle);
        this.add(title).padBottom(20).colspan(4).row();

        // Okienko opisu (na początku niewidoczne)
        Texture descBgTexture = new Texture(Gdx.files.internal("upgradeButton.png"));
        Drawable descBackground = new TextureRegionDrawable(new TextureRegion(descBgTexture));

        descWindow = new Window("", skin);
        descWindow.setBackground(descBackground);
        descWindow.setVisible(false);
        descWindow.setMovable(false);
        descWindow.setKeepWithinStage(true);
        this.addActor(descWindow);

        int col = 0;
        for (Item item : items) {
            Texture texture = new Texture(Gdx.files.internal("items/"+ item.getImage()));
            Image image = new Image(new TextureRegion(texture));

            // Stwórz ImageButton bez tła
            ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
            imageButtonStyle.up = null; // Brak tła
            imageButtonStyle.down = null; // Brak tła po kliknięciu
            imageButtonStyle.over = null; // Brak tła po najechaniu

            ImageButton button = new ImageButton(imageButtonStyle);
            button.add(image).size(140, 140);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (item.buy(state, incomeManager)) {
                        button.setDisabled(true);
                        // Ustaw szary kolor dla obrazka po zakupie
                        image.setColor(0.5f, 0.5f, 0.5f, 1f);
                    }
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    // Ustaw tekst i pozycję okienka opisu
                    descWindow.clear();
                    descWindow.add(new Label(item.getDescription(), labelStyle)).pad(20);
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
