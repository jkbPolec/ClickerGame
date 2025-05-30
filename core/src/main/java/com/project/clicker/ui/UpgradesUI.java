package com.project.clicker.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.project.clicker.logic.GameState;
import com.project.clicker.logic.Upgrade.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class UpgradesUI {
    private final Table container;
    private final List<Label> upgradeLabels = new ArrayList<>();
    private final List<ImageTextButton> upgradeButtons = new ArrayList<>();

    public UpgradesUI(Skin skin, GameState state) {
        Table upgradeTable = new Table();

        for (Upgrade upgrade : state.getUpgrades()) {
            Table buttonTable = new Table();
            ImageTextButton upgradeButton = new ImageTextButton(".", skin, "upgrade-button");
            Label description = new Label(upgrade.getUpgradeInfo(), skin, "upgrade-description");

            upgradeButton.setDisabled(false);
            upgradeButton.setTouchable(Touchable.enabled);

            upgradeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    upgrade.apply();
                }
            });

            upgradeLabels.add(description);
            upgradeButtons.add(upgradeButton);
            buttonTable.add(upgradeButton).expand().left();
            buttonTable.add(description).expand().right();
            buttonTable.setBackground(skin.getDrawable("button"));
            upgradeTable.add(buttonTable).size(600, 150).padBottom(50).row();
        }

        ScrollPane scrollPane = new ScrollPane(upgradeTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(false, false);

        container = new Table();
        container.top().padTop(50);
        container.add(scrollPane).grow();
        container.setSize(100, 100);
    }

    public Table getContainer() {
        return container;
    }

    public void update(GameState state) {
        for (int i = 0; i < state.getUpgrades().size(); i++) {
            Upgrade upgrade = state.getUpgrades().get(i);
            upgradeLabels.get(i).setText(upgrade.getUpgradeInfo());
            ImageTextButton button = upgradeButtons.get(i);
            button.setText("Przycisk");

            if (upgrade.getCost() <= state.getMoney()) {
                button.setDisabled(false);
                button.setTouchable(Touchable.enabled);
            } else {
                button.setDisabled(true);
                button.setTouchable(Touchable.disabled);
            }
        }
    }
}
