package com.project.clicker.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.project.clicker.logic.BigNumber;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.managers.IncomeManager;
import com.project.clicker.sound.SoundManager;

public class ClickingUI {
    private final Table table;
    private final Label clickLabel;
    private final Label moneyLabel;
    private final Label passiveIncomeLabel;
    BigNumber money;

    public ClickingUI(Skin skin, GameState state, IncomeManager incomeManager) {


        // Styl Labela bez tła i z niestandardową czcionką
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("pixel_font_clickingUI.fnt"));
        labelStyle.fontColor = Color.WHITE;


        table = new Table();
        clickLabel = new Label("Clicks: 0", labelStyle);


        ImageButton imageButton = new ImageButton(skin, "dollar-button");
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(state.getClicks());
                state.addClicks(1);
                System.out.println(state.getClicks());
                incomeManager.processClick();

                clickLabel.setText("Clicks: " + state.getClicks());
                SoundManager.getInstance().playRandomClick();

                // Animacja
                imageButton.clearActions();
                imageButton.addAction(
                    Actions.sequence(
                        Actions.scaleTo(0.85f, 0.85f, 0.08f), // zmniejsz
                        Actions.scaleTo(1.1f, 1.1f, 0.08f),   // powiększ lekko
                        Actions.scaleTo(1f, 1f, 0.08f)        // wróć do normalnego rozmiaru
                    )
                );
            }
        });

        // Do animacji
        imageButton.setOrigin(imageButton.getWidth() / 2f, imageButton.getHeight() / 2f);
        imageButton.setTransform(true);
        imageButton.setScale(1f);

        table.add(clickLabel).row();
        table.add(imageButton).size(300, 300).row();
        imageButton.setOrigin(150, 150);

        moneyLabel = new Label("Money: 0$", labelStyle);
        passiveIncomeLabel = new Label("Passive income: " + incomeManager.getCurrentPassiveIncome() + "$", labelStyle);
        table.add(moneyLabel).row();
        table.add(passiveIncomeLabel).row();
    }

    public Table getTable() {
        return table;
    }

    public void update(GameState state, IncomeManager incomeManager) {
        clickLabel.setText("Clicks: " + state.getClicks());
        money = state.getMoney();
        moneyLabel.setText("Money: " + money.toReadableString() + "$");
        passiveIncomeLabel.setText("Passive income: " + incomeManager.getCurrentPassiveIncome() + "$");
    }
}
