package com.monisha.bsafe.tips.data;

import com.monisha.bsafe.R;

import java.util.ArrayList;
import java.util.List;

public class TipItemList {
    private List<TipItem> items = new ArrayList<>();

    public List<TipItem> getItems() {
        if (items.isEmpty()) {
            addItemsToList();
        }
        return items;
    }

    private void addItemsToList() {
        items.add(new TipItem(R.string.out_alone, R.drawable.alonegirl));
        items.add(new TipItem(R.string.out_alone_tip1));
        items.add(new TipItem(R.string.out_alone_tip2));

        items.add(new TipItem(R.string.travelling, R.drawable.travelling));
        items.add(new TipItem(R.string.travelling_tip1));
        items.add(new TipItem(R.string.travelling_tip2));
        items.add(new TipItem(R.string.travelling_tip3));

        items.add(new TipItem(R.string.shopping, R.drawable.shopping));
        items.add(new TipItem(R.string.shopping_tip1));
        items.add(new TipItem(R.string.shopping_tip2));
        items.add(new TipItem(R.string.shopping_tip3));

        items.add(new TipItem(R.string.at_home, R.drawable.homee));
        items.add(new TipItem(R.string.at_home_tip1));
        items.add(new TipItem(R.string.at_home_tip3));
        items.add(new TipItem(R.string.at_home_tip2));

        items.add(new TipItem(R.string.in_the_car, R.drawable.carr));
        items.add(new TipItem(R.string.in_the_car_tip1));
        items.add(new TipItem(R.string.in_the_car_tip3));
        items.add(new TipItem(R.string.in_the_car_tip2));

        items.add(new TipItem(R.string.in_parties, R.drawable.party));
        items.add(new TipItem(R.string.in_parties_tip1));
        items.add(new TipItem(R.string.in_parties_tip3));
        items.add(new TipItem(R.string.in_parties_tip2));

        items.add(new TipItem(R.string.online, R.drawable.customer));
        items.add(new TipItem(R.string.online_tip1));
        items.add(new TipItem(R.string.online_tip3));
        items.add(new TipItem(R.string.online_tip2));
    }
}
