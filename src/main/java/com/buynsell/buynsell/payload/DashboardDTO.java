package com.buynsell.buynsell.payload;



import com.buynsell.buynsell.model.Item;

import java.util.List;

public class DashboardDTO {
    List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
