package com.project.clicker.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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
import com.project.clicker.logic.Upgrade.Upgrade;
import com.project.clicker.logic.Upgrade.UpgradeInfo;
import com.project.clicker.logic.Upgrade.NormalUpgradeType;

import java.util.*;
import java.util.List;

public class UpgradesUI {
    private final Table container;
    private final List<Button> upgradeButtons = new ArrayList<>();
    private final List<Label> costLabels = new ArrayList<>(); // Do aktualizacji kolorów tekstu
    private final List<Label> effectsLabels = new ArrayList<>(); // Do aktualizacji tekstu efektów
    BigNumber money;

    public UpgradesUI(Skin skin, GameState state) {
        Table upgradeTable = new Table();

        Texture texture = new Texture("upgradeButton.png");
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        TextureRegion region = new TextureRegion(texture);

        BitmapFont pixelFont = new BitmapFont(Gdx.files.internal("pixel_font.fnt"));

        for (Upgrade upgrade : state.getUpgrades()) {
            UpgradeInfo info = upgrade.getUpgradeInfo();

            // OPCJA 1: Używaj tylko Button.ButtonStyle bez dodatkowego tła
            Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
            buttonStyle.up = new TextureRegionDrawable(region);
            buttonStyle.down = new TextureRegionDrawable(region).tint(Color.GRAY);

            // Poprawne tworzenie disabled drawable
            TextureRegionDrawable disabledDrawable = new TextureRegionDrawable(region);
            disabledDrawable.setMinWidth(region.getRegionWidth());
            disabledDrawable.setMinHeight(region.getRegionHeight());
            buttonStyle.disabled = disabledDrawable.tint(Color.DARK_GRAY);

            Button button = new Button(buttonStyle);

            // Zawartość przycisku BEZ dodatkowego tła
            Label nameLabel = new Label(info.name, new Label.LabelStyle(pixelFont, Color.WHITE));
            nameLabel.setAlignment(Align.left);

            Label costLabel = new Label(info.cost.toReadableString() + "$", new Label.LabelStyle(pixelFont, Color.WHITE));
            costLabel.setAlignment(Align.right);
            costLabels.add(costLabel); // Zapisz referencję do aktualizacji koloru

            Table content = new Table();
            content.add(nameLabel).expandX().left().padLeft(20);
            content.add(costLabel).expandX().right().padRight(20);
            content.row();

            Label effectsLabel = new Label(buildEffectsText(info), new Label.LabelStyle(pixelFont, Color.LIGHT_GRAY));
            effectsLabel.setWrap(true);
            effectsLabels.add(effectsLabel); // Zapisz referencję do aktualizacji
            content.add(effectsLabel).colspan(2).width(560).padTop(10).left();

            button.add(content).width(600).height(150);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!button.isDisabled()) { // Dodatkowa ochrona
                        upgrade.apply();
                        // Natychmiastowa aktualizacja po kliknięciu
                        UpgradeInfo updatedInfo = upgrade.getUpgradeInfo();
                        costLabel.setText(updatedInfo.cost.toReadableString() + "$");
                        effectsLabel.setText(buildEffectsText(updatedInfo));
                    }
                }
            });

            upgradeButtons.add(button);
            upgradeTable.add(button).width(600).height(150).padBottom(50).row();
        }


        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();

        try {
            // Wczytaj własne tekstury dla scrolla
            Texture scrollKnobTexture = new Texture("scroll_knob.png");

            scrollKnobTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

            // Stwórz TextureRegionDrawable z ustalonymi wymiarami
            TextureRegionDrawable scrollKnobDrawable = new TextureRegionDrawable(new TextureRegion(scrollKnobTexture));

            Pixmap barPixmap = new Pixmap(16, 100, Pixmap.Format.RGBA8888); // 100 px wysokości przykładowo
            barPixmap.setColor(191f / 255f, 161f / 255f, 85f / 255f, 1f); // ciemniejszy brąz
            barPixmap.fill();
            Texture barTexture = new Texture(barPixmap);
            TextureRegionDrawable scrollBarDrawable = new TextureRegionDrawable(new TextureRegion(barTexture));

            // KLUCZ: Ustaw minimalne wymiary dla scrolla
            scrollKnobDrawable.setMinWidth(24);  // Szerokość uchwytu scrolla
            scrollKnobDrawable.setMinHeight(48); // Wysokość uchwytu scrolla

            scrollBarDrawable.setMinWidth(16);   // Szerokość paska scrolla
            scrollBarDrawable.setMinHeight(16);  // Wysokość elementu paska scrolla

            scrollStyle.vScrollKnob = scrollKnobDrawable;
            scrollStyle.vScroll = scrollBarDrawable;

        } catch (Exception e) {
            // Fallback - użyj domyślnego stylu ale zmień rozmiar
            scrollStyle = new ScrollPane.ScrollPaneStyle(skin.get(ScrollPane.ScrollPaneStyle.class));

            // Jeśli masz dostęp do domyślnych drawable, też możesz ustawić ich rozmiar
            if (scrollStyle.vScrollKnob != null) {
                scrollStyle.vScrollKnob.setMinWidth(12);
                scrollStyle.vScrollKnob.setMinHeight(24);
            }
            if (scrollStyle.vScroll != null) {
                scrollStyle.vScroll.setMinWidth(12);
                scrollStyle.vScroll.setMinHeight(12);
            }
        }

        ScrollPane scrollPane = new ScrollPane(upgradeTable, scrollStyle);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(false, false);

// Dodatkowe ustawienia dla lepszej kontroli nad scrollem
        scrollPane.setScrollBarPositions(false, true); // Scroll bar po prawej stronie
        scrollPane.setVariableSizeKnobs(false); // Stały rozmiar uchwytu scrolla

        container = new Table();
        container.top();
        container.add(scrollPane).grow();
        container.setSize(100, 100);
    }

    public Table getContainer() {
        return container;
    }

    public void update(GameState state) {
        this.money = state.getMoney();
        for (int i = 0; i < state.getUpgrades().size(); i++) {
            Upgrade upgrade = state.getUpgrades().get(i);
            UpgradeInfo info = upgrade.getUpgradeInfo();
            Button button = upgradeButtons.get(i);
            Label costLabel = costLabels.get(i);

            boolean affordable = money.isGreaterOrEqual(info.cost);

            // Ustaw stan przycisku
            button.setDisabled(!affordable);
            button.setTouchable(affordable ? Touchable.enabled : Touchable.disabled);

            // Zmień kolor tekstu ceny dla lepszego efektu wizualnego
            costLabel.setColor(affordable ? Color.WHITE : Color.RED);

            // Aktualizuj tekst ceny (na wypadek zmiany)
            costLabel.setText(info.cost.toReadableString() + "$");
            Label effectsLabel = effectsLabels.get(i);
            effectsLabel.setText(buildEffectsText(info));

        }
    }

    private String buildEffectsText(UpgradeInfo info) {
        StringBuilder sb = new StringBuilder();

        if (info.normalEffects != null) {
            for (Map.Entry<NormalUpgradeType, Double> entry : info.normalEffects.entrySet()) {
                sb.append("  ").append(entry.getKey()).append(": x").append(entry.getValue()).append("\n");
            }
        }

        if (info.buildingType != null) {
            sb.append("  Buduje: ").append(info.buildingType).append("\n");
        }

        sb.append("  Użyto: ").append(info.timesActivated).append("x");
        if (info.isBonus) {
            sb.append(" [BONUS]");
        }

        return sb.toString();
    }
}
