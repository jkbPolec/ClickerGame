package com.project.clicker.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.ResetManager;

public class SPButtonUI {
    private final Table container;
    private final ResetManager resetManager;
    private final GameState state;

    private final Button btnSP1;
    private final Button btnSP10;
    private final Button btnSP100;
    private final Label costLabel1;
    private final Label costLabel10;
    private final Label costLabel100;

    public SPButtonUI(Skin skin, ResetManager resetManager, GameState state) {
        this.resetManager = resetManager;
        this.state = state;
        this.container = new Table();

        // Ładowanie tekstury i fontu
        Texture texture = new Texture("upgradeButton.png");
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        TextureRegion region = new TextureRegion(texture);

        BitmapFont pixelFont = new BitmapFont(Gdx.files.internal("pixel_font.fnt"));

        // Tworzenie stylu przycisków
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(region);
        buttonStyle.down = new TextureRegionDrawable(region).tint(Color.GRAY);

        TextureRegionDrawable disabledDrawable = new TextureRegionDrawable(region);
        disabledDrawable.setMinWidth(region.getRegionWidth());
        disabledDrawable.setMinHeight(region.getRegionHeight());
        buttonStyle.disabled = disabledDrawable.tint(Color.DARK_GRAY);

        // Utworzenie pierwszego przycisku SP x1
        btnSP1 = new Button(new Button.ButtonStyle(buttonStyle));

        Label nameLabel1 = new Label("Buy SP x1", new Label.LabelStyle(pixelFont, Color.WHITE));
        nameLabel1.setAlignment(Align.left);

        costLabel1 = new Label("1.00M$", new Label.LabelStyle(pixelFont, Color.WHITE));
        costLabel1.setAlignment(Align.right);

        Table content1 = new Table();
        content1.add(nameLabel1).expandX().left().padLeft(20);
        content1.add(costLabel1).expandX().right().padRight(20);
        content1.row();



        btnSP1.add(content1).width(320).height(100);

        btnSP1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!btnSP1.isDisabled()) {
                    resetManager.purchaseSpecialPoints(1);
                    update();
                }
            }
        });

        // Utworzenie drugiego przycisku SP x10
        btnSP10 = new Button(new Button.ButtonStyle(buttonStyle));

        Label nameLabel10 = new Label("Buy SP x10", new Label.LabelStyle(pixelFont, Color.WHITE));
        nameLabel10.setAlignment(Align.left);

        costLabel10 = new Label("10.0M$", new Label.LabelStyle(pixelFont, Color.WHITE));
        costLabel10.setAlignment(Align.right);

        Table content10 = new Table();
        content10.add(nameLabel10).expandX().left().padLeft(20);
        content10.add(costLabel10).expandX().right().padRight(20);
        content10.row();



        btnSP10.add(content10).width(320).height(100);

        btnSP10.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!btnSP10.isDisabled()) {
                    resetManager.purchaseSpecialPoints(10);
                    update();
                }
            }
        });

        // Utworzenie trzeciego przycisku SP x100
        btnSP100 = new Button(new Button.ButtonStyle(buttonStyle));

        Label nameLabel100 = new Label("Buy SP x100", new Label.LabelStyle(pixelFont, Color.WHITE));
        nameLabel100.setAlignment(Align.left);

        costLabel100 = new Label("100M$", new Label.LabelStyle(pixelFont, Color.WHITE));
        costLabel100.setAlignment(Align.right);

        Table content100 = new Table();
        content100.add(nameLabel100).expandX().left().padLeft(20);
        content100.add(costLabel100).expandX().right().padRight(20);
        content100.row();



        btnSP100.add(content100).width(320).height(100);

        btnSP100.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!btnSP100.isDisabled()) {
                    resetManager.purchaseSpecialPoints(100);
                    update();
                }
            }
        });
        container.left();
        // Dodanie przycisków do kontenera
        container.add(btnSP1).pad(5).width(320).height(100).row();
        container.add(btnSP10).pad(5).width(320).height(100).row();
        container.add(btnSP100).pad(5).width(320).height(100);
    }

    public void update() {
        // Sprawdzanie czy gracz ma wystarczająco pieniędzy dla każdego przycisku
        BigNumber cost1 = new BigNumber(1000000);
        BigNumber cost10 = new BigNumber(1000000).multiply(new BigNumber(10));
        BigNumber cost100 = new BigNumber(1000000).multiply(new BigNumber(100));

        // Aktualizacja pierwszego przycisku
        boolean affordable1 = state.getMoney().isGreaterOrEqual(cost1);
        btnSP1.setDisabled(!affordable1);
        btnSP1.setTouchable(affordable1 ? Touchable.enabled : Touchable.disabled);
        costLabel1.setColor(affordable1 ? Color.WHITE : Color.RED);
        costLabel1.setText(cost1.toReadableString() + "$");

        // Aktualizacja drugiego przycisku
        boolean affordable10 = state.getMoney().isGreaterOrEqual(cost10);
        btnSP10.setDisabled(!affordable10);
        btnSP10.setTouchable(affordable10 ? Touchable.enabled : Touchable.disabled);
        costLabel10.setColor(affordable10 ? Color.WHITE : Color.RED);
        costLabel10.setText(cost10.toReadableString() + "$");

        // Aktualizacja trzeciego przycisku
        boolean affordable100 = state.getMoney().isGreaterOrEqual(cost100);
        btnSP100.setDisabled(!affordable100);
        btnSP100.setTouchable(affordable100 ? Touchable.enabled : Touchable.disabled);
        costLabel100.setColor(affordable100 ? Color.WHITE : Color.RED);
        costLabel100.setText(cost100.toReadableString() + "$");
    }

    public Table getContainer() {
        return container;
    }

    public void setPosition(float x, float y) {
        container.setPosition(x, y);
    }
}
