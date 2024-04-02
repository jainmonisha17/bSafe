package com.monisha.bsafe.tips;

import android.os.Bundle;

import com.monisha.bsafe.BaseLanguageLoaderActivity;
import com.monisha.bsafe.databinding.ActivityTipsBinding;
import com.monisha.bsafe.tips.data.EscapeItemList;
import com.monisha.bsafe.tips.data.TipItemList;

public class TipsActivity extends BaseLanguageLoaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTipsBinding binding = ActivityTipsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // setting items in the Recycler view
        if (getIntent().hasExtra("escape")) {
            EscapeItemList list = new EscapeItemList();
            TipsAdapter recyclerViewAdapter = new TipsAdapter(list.getItems());
            binding.recyclerView.setAdapter(recyclerViewAdapter);
        } else {
            TipItemList list = new TipItemList();
            TipsAdapter recyclerViewAdapter = new TipsAdapter(list.getItems());
            binding.recyclerView.setAdapter(recyclerViewAdapter);
        }
    }
}