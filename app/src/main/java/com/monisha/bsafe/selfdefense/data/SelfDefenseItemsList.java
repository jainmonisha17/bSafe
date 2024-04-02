package com.monisha.bsafe.selfdefense.data;

import com.monisha.bsafe.R;

import java.util.ArrayList;
import java.util.List;

public class SelfDefenseItemsList {
    private List<SelfDefenseItem> items = new ArrayList<>();

    public List<SelfDefenseItem> getItems() {
        if (items.isEmpty()) {
            addItemsToList();
        }
        return items;
    }

    private void addItemsToList() {
        items.add(new SelfDefenseItem(R.string.self_defense_one, R.raw.one));
        items.add(new SelfDefenseItem(R.string.self_defense_two, R.raw.two));
        items.add(new SelfDefenseItem(R.string.self_defense_three, R.raw.three));
        items.add(new SelfDefenseItem(R.string.self_defense_four, R.raw.four));
    }
}
