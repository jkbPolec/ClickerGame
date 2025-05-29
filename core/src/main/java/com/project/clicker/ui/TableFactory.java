package com.project.clicker.ui;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TableFactory {

    private final Skin skin;

    public TableFactory() {
        this.skin = SkinManager.getInstance().skin;
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


    int clicks = 0;
    public Table createClickingMenu() {

        Table table = new Table();

        Label clickLabel = new Label("Kliknięcia: 0", skin);

        ImageButton imageButton = new ImageButton(skin, "dollar-button");
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                clicks ++;
                clickLabel.setText("Kliknięcia: " + clicks);
            }


        });


        table.add(clickLabel).row();
        table.add(imageButton).size(300, 300);

        return table;
    }


    public Table getScrollableMenu(String titleText, String... buttonLabels) {
        Table table = new Table();
        Label title = new Label(titleText, skin);
        table.add(title).padBottom(30).row();

        for (String label : buttonLabels) {
            Table buttonTable = new Table();


            ImageTextButton button = new ImageTextButton("ulepszenie", skin, "upgrade-button");
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Tutaj możesz dodać logikę dla przycisku ulepszenia
                    System.out.println("Ulepszenie: " + label);
                }
            });

            Label description = new Label("Level: " + label, skin, "upgrade-description"); // Przykładowy opis
            description.setFontScale(0.7f);

            buttonTable.add(button).expand().left();
            buttonTable.add(description).expand().right();
            buttonTable.setBackground(skin.getDrawable("button"));
            table.add(buttonTable).size(600,75).padBottom(50).row();
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
