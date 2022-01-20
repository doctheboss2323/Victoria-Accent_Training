package com.labhall.languagevoicelearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class LearningActivity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private EditText editText;
    private ImageView micButton;

//    public static final Integer RecordAudioRequestCode = 1;
//    private SpeechRecognizer speechRecognizer;
//    TextView textlanguage2, textlevel;
//    EditText editText, editText2;
//    ImageView buttonMic;

    int language = 0, level = 1, wordnumber = 2;  //words starts at line 3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int language = extras.getInt("language");
        }

//        editText = findViewById(R.id.text);
//        buttonMic = findViewById(R.id.buttonmic);
//        editText2 = findViewById(R.id.edittext2);


    }


}