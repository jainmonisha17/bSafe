package com.monisha.bsafe;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.monisha.bsafe.appinfo.language.LanguageInfo;

import java.util.Locale;

public class BaseLanguageLoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load selected language
        String languageToLoad = LanguageInfo.getLanguage(this);
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_base);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}