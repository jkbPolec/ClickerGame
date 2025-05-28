package com.project.clicker.ui;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class TableFactory {

    private Skin skin;

    public TableFactory(Skin skin) {
        this.skin = skin;
    }

    public Table createSimpleMenu(String titleText, String... buttonLabels) {
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label title = new Label(titleText, skin);
        table.add(title).padBottom(30).row();

        for (String label : buttonLabels) {
            TextButton button = new TextButton(label, skin);
            table.add(button).pad(10).row();
        }

        return table;
    }


    public Table createScrollableMenu(String titleText, String... buttonLabels) {
        Table table = new Table();
        Label title = new Label(titleText, skin);
        table.add(title).padBottom(30).row();

        for (String label : buttonLabels) {
            Table buttonTable = new Table();
            TextButton button = new TextButton(label, skin);
            Label description = new Label("Level: " + label, skin, "upgrade-description"); // Przykładowy opis
            description.setFontScale(0.7f);
            buttonTable.add(button).expand().left();
            buttonTable.add(description).expand().right(); // Dodaj opis pod przyciskiem
            buttonTable.setBackground(skin.getDrawable("button"));
            table.add(buttonTable).size(400,75).pad(10).row();
            //table.add(button).pad(10).row();
        }



        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(false, false); // pozwala przewijać pionowo i poziomo (możesz ustawić na true jeśli chcesz tylko pion)

        Table container = new Table();
        //container.setFillParent(true);
        container.top().padTop(50);
        container.add(scrollPane).grow(); // scrollPane zajmie całą dostępną przestrzeń

        container.setSize(100, 100);
        return container;
    }


    public Table createForm(String[] labels) {
        Table table = new Table();
        table.setFillParent(true);
        table.top().left().pad(20);

        for (String labelText : labels) {
            Label label = new Label(labelText, skin);
            table.add(label).pad(5);
            table.row();
        }

        return table;
    }
}
