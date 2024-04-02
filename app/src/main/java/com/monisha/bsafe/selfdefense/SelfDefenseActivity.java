package com.monisha.bsafe.selfdefense;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.monisha.bsafe.BaseLanguageLoaderActivity;
import com.monisha.bsafe.databinding.ActivitySelfDefenseBinding;
import com.monisha.bsafe.selfdefense.data.EmergencyButtonClickListener;
import com.monisha.bsafe.selfdefense.data.EmergencyItemsList;
import com.monisha.bsafe.selfdefense.data.SelfDefenseItemsList;

public class SelfDefenseActivity extends BaseLanguageLoaderActivity implements EmergencyButtonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelfDefenseBinding binding = ActivitySelfDefenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("self_defense")) {
            SelfDefenseItemsList list = new SelfDefenseItemsList();
            SelfDefenseAdapter recyclerViewAdapter = new SelfDefenseAdapter(list.getItems(), null);
            binding.recyclerView.setAdapter(recyclerViewAdapter);
        } else {
            EmergencyItemsList list = new EmergencyItemsList();
            SelfDefenseAdapter recyclerViewAdapter = new SelfDefenseAdapter(list.getItems(), this);
            binding.recyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    @Override
    public void onItemClick(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        startActivity(callIntent);
    }
}