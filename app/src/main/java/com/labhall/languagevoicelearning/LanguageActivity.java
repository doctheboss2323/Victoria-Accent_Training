package com.labhall.languagevoicelearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LanguageActivity extends AppCompatActivity {

    TextView textLanguage;
    Button buttonEnglish,buttonSpanish,buttonItalian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        textLanguage=findViewById(R.id.textlanguage);
        buttonEnglish=findViewById(R.id.buttonenglish);
        buttonSpanish=findViewById(R.id.buttonspanish);
        buttonItalian=findViewById(R.id.buttonitalian);

        buttonEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(2);
            }
        });

        buttonSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(1);
            }
        });

        buttonItalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(3);
            }
        });
    }
    public void nextActivity(int language){
        Intent i = new Intent(LanguageActivity.this, LearningActivity.class);
        i.putExtra("language",language);
        startActivity(i);  }
}