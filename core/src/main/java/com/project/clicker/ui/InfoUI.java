package com.project.clicker.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeMultipliers;

public class InfoUI {
    private final Label label;

    public InfoUI(Skin skin, IncomeMultipliers incomeMultipliers, GameState gameState) {
        // Styl bez tła i z czcionką
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("pixel_font_MultipliersUI.fnt"));
        labelStyle.fontColor = Color.WHITE;

        label = new Label("", labelStyle);
        label.setWrap(true);
        label.setAlignment(Align.left);

        update(incomeMultipliers, gameState);
    }

    public void update(IncomeMultipliers incomeMultipliers, GameState state) {


        label.setText(incomeMultipliers.getMultipliersInfo() + state.getStateInfo());

    }

    public Label getLabel() {
        return label;
    }
}
