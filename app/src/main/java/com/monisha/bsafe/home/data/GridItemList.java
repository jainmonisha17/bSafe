package com.monisha.bsafe.home.data;

import com.monisha.bsafe.R;

import java.util.ArrayList;
import java.util.List;

public class GridItemList {
    private List<GridItem> items = new ArrayList<>();

    public List<GridItem> getItems() {
        if (items.isEmpty()) {
            addItemsToList();
        }
        return items;
    }

    private void addItemsToList() {
        items.add(new GridItem(GridItemType.CALL_POLICE, R.string.call_police, R.drawable.call_police));
        items.add(new GridItem(GridItemType.ADD_GUARDIANS, R.string.add_guardians, R.drawable.guardians));
        items.add(new GridItem(GridItemType.SAFETY_TIPS, R.string.women_safety_tips, R.drawable.women_safety));
        items.add(new GridItem(GridItemType.NEARBY_POLICE_STATIONS, R.string.show_nearby_police_stations, R.drawable.policebadge));
        items.add(new GridItem(GridItemType.CHANGE_LANGUAGE, R.string.change_language, R.drawable.language));
        items.add(new GridItem(GridItemType.ESCAPE, R.string.escape, R.drawable.running));
        items.add(new GridItem(GridItemType.SELF_DEFENSE, R.string.self_defense, R.drawable.self_defense));
        items.add(new GridItem(GridItemType.EMERGENCY_NUMBERS, R.string.emergency_numbers, R.drawable.emergency_call));
    }
}
