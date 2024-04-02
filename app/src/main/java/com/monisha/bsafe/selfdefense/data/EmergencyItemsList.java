package com.monisha.bsafe.selfdefense.data;

import com.monisha.bsafe.R;

import java.util.ArrayList;
import java.util.List;

public class EmergencyItemsList {
    private List<SelfDefenseItem> items = new ArrayList<>();

    public List<SelfDefenseItem> getItems() {
        if (items.isEmpty()) {
            addItemsToList();
        }
        return items;
    }

    private void addItemsToList() {
        items.add(new SelfDefenseItem(R.string.emergency_ambulance, R.drawable.ambulance, "108"));
        items.add(new SelfDefenseItem(R.string.emergency_women_helpline, R.drawable.protection, "1091"));
        items.add(new SelfDefenseItem(R.string.emergency_NCW, R.drawable.support, "011-26942369"));
        items.add(new SelfDefenseItem(R.string.emergency_helpline, R.drawable.emergency_call, "112"));
    }
}
