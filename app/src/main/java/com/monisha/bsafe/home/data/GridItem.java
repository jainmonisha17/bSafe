package com.monisha.bsafe.home.data;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class GridItem {
    private final GridItemType type;
    private @StringRes final int name;
    private @DrawableRes final int drawableRes;

    public GridItem(GridItemType type, int name, int imageRes) {
        this.type = type;
        this.name = name;
        this.drawableRes = imageRes;
    }

    @StringRes
    public int getName() {
        return name;
    }

    @DrawableRes
    public int getDrawableRes() {
        return drawableRes;
    }

    public GridItemType getType() {
        return type;
    }
}
