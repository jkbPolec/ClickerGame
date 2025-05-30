package com.project.clicker.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.IncomeManager;

public class ClickingUI {
    private final Table table;
    private final Label clickLabel;
    private final Label moneyLabel;
    private final Label passiveIncomeLabel;

    public ClickingUI(Skin skin, GameState state, IncomeManager incomeManager) {
        table = new Table();
        clickLabel = new Label("Kliknięcia: 0", skin);

        ImageButton imageButton = new ImageButton(skin, "dollar-button");
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                state.addClicks(1);
                incomeManager.addMoneyFromClick();
                clickLabel.setText("Kliknięcia: " + state.getClicks());

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

        moneyLabel = new Label("Money: 0", skin);
        passiveIncomeLabel = new Label("Pas. przyrost: " + incomeManager.getPassiveIncome(), skin);
        table.add(moneyLabel).row();
        table.add(passiveIncomeLabel).row();
    }

    public Table getTable() {
        return table;
    }

    public void update(GameState state, IncomeManager incomeManager) {
        moneyLabel.setText("Money: " + state.getMoney());
        passiveIncomeLabel.setText("Pas. przyrost: " + incomeManager.getPassiveIncome());
    }
}
