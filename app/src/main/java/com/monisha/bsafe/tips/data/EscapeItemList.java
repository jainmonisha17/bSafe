package com.monisha.bsafe.tips.data;

import com.monisha.bsafe.R;

import java.util.ArrayList;
import java.util.List;

public class EscapeItemList {
    private List<TipItem> items = new ArrayList<>();

    public List<TipItem> getItems() {
        if (items.isEmpty()) {
            addItemsToList();
        }
        return items;
    }

    private void addItemsToList() {
        items.add(new TipItem(R.string.escape_q1, R.drawable.elevator));
        items.add(new TipItem(R.string.escape_ans1));

        items.add(new TipItem(R.string.escape_q2, R.drawable.worker));
        items.add(new TipItem(R.string.escape_ans2));

        items.add(new TipItem(R.string.escape_q3, R.drawable.taxi));
        items.add(new TipItem(R.string.escape_ans3));

        items.add(new TipItem(R.string.escape_q4, R.drawable.fear));
        items.add(new TipItem(R.string.escape_ans4));

        items.add(new TipItem(R.string.escape_q5, R.drawable.driver));
        items.add(new TipItem(R.string.escape_ans5));

    }
}
