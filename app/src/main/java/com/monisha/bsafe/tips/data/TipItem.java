package com.monisha.bsafe.tips.data;


import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class TipItem {
    private @StringRes
    int heading = -1;
    private @DrawableRes
    int headingImage = -1;
    private @StringRes int tip = -1;

    public TipItem(int heading, int headingImage) {
        this.heading = heading;
        this.headingImage = headingImage;
    }

    public TipItem(int tip) {
        this.tip = tip;
    }

    public int getHeading() {
        return heading;
    }

    public int getHeadingImage() {
        return headingImage;
    }

    public int getTip() {
        return tip;
    }
}
