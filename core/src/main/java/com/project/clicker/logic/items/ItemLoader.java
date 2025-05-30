package com.project.clicker.logic.items;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ItemLoader {

    private final List<Item> items = new ArrayList<>();

    public ItemLoader(String resourceName) {
        Gson gson = new Gson();
        InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (input == null) {
            throw new IllegalArgumentException("Nie znaleziono pliku: " + resourceName);
        }
        List<ItemData> itemsData = gson.fromJson(new InputStreamReader(input), new TypeToken<List<ItemData>>(){}.getType());
        for (ItemData data : itemsData) {
            items.add(ItemFactory.createItem(data));
        }
    }

    public List<Item> getItems() {
        return items;
    }
}
