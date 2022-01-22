package com.labhall.languagevoicelearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class LanguageActivity extends AppCompatActivity {

    TextView textLanguage;
    Button buttonEnglish,buttonSpanish,buttonItalian;
    Button buttonlvlEnglish,buttonlvlSpanish,buttonlvlItalian;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        sharedpreferences = getSharedPreferences("mypref",Context.MODE_PRIVATE);

        textLanguage=findViewById(R.id.textlanguage);
        buttonEnglish=findViewById(R.id.buttonenglish);
        buttonSpanish=findViewById(R.id.buttonspanish);
        buttonItalian=findViewById(R.id.buttonitalian);

        buttonlvlEnglish=findViewById(R.id.buttonlevelenglish);
        buttonlvlSpanish=findViewById(R.id.buttonlevelspanish);
        buttonlvlItalian=findViewById(R.id.buttonlevelitalian);

        activatelevels();

        buttonEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bounceAnim(buttonEnglish);
                nextActivity(2);
            }
        });

        buttonSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bounceAnim(buttonSpanish);
                nextActivity(1);
            }
        });

        buttonItalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bounceAnim(buttonItalian);
                nextActivity(3);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activatelevels();
    }

    private void activatelevels() {
        int wordnumber=sharedpreferences.getInt("wordnumberkeyenglish", 2);
        int level=(wordnumber-1)/30+1; //later set to 30 for 30 words each level
        buttonlvlEnglish.setText("Lvl "+ level);
        wordnumber=sharedpreferences.getInt("wordnumberkeyspanish", 2);
        level=(wordnumber-1)/30+1; //later set to 30 for 30 words each level
        buttonlvlSpanish.setText("Lvl "+ level);
        wordnumber=sharedpreferences.getInt("wordnumberkeyitalian", 2);
        level=(wordnumber-1)/30+1; //later set to 30 for 30 words each level
        buttonlvlItalian.setText("Lvl "+ level);
    }

    public void nextActivity(int language){
        Intent i = new Intent(LanguageActivity.this, LearningActivity.class);
        i.putExtra("language",language);
        startActivity(i);  }

    public void bounceAnim(Button button){
        ObjectAnimator animY = ObjectAnimator.ofFloat(button, "translationY", -50f, 0f);
        animY.setRepeatMode(ValueAnimator.REVERSE);
        animY.setDuration(100);//1sec
        animY.setInterpolator(new BounceInterpolator());
        animY.setRepeatCount(0);
        animY.start();
    }
}
