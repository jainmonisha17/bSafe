package com.monisha.bsafe.selfdefense.data;

import androidx.annotation.StringRes;

public class SelfDefenseItem {
    @StringRes
    private int title = -1;
    private int picture = -1;
    private String number = "";

    public SelfDefenseItem(int title, int picture, String number) {
        this.title = title;
        this.picture = picture;
        this.number = number;
    }

    public SelfDefenseItem(int title, int gif) {
        this.title = title;
        this.picture = gif;
    }

    public int getTitle() {
        return title;
    }

    public int getPicture() {
        return picture;
    }

    public String getNumber() {
        return number;
    }
}
