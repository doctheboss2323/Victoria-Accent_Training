package com.labhall.languagevoicelearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import static android.icu.lang.UCharacter.toLowerCase;

public class LearningActivity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private TextView textViewWord,textViewLanguage,textViewLevel,textViewWordNumber;
    private ImageView micButton;


    int language, level = 1, wordnumber;  //words starts at line 3 - language 2 is english
    String word="";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

        textViewWord = findViewById(R.id.textviewword);
        textViewLanguage = findViewById(R.id.textlanguage2);
        textViewLevel=findViewById(R.id.textlevel);
        textViewWordNumber=findViewById(R.id.textwordnumber);
        micButton = findViewById(R.id.imagemic);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        sharedpreferences = getSharedPreferences("mypref",Context.MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();  //get chosen language
        if (extras != null) {
            int language = extras.getInt("language");

            if(language==1){
                textViewLanguage.setText("Spanish");
                speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
                wordnumber=sharedpreferences.getInt("wordnumberkeyspanish", 2);
            }
            else if(language==2){
                textViewLanguage.setText("English");
                speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "eng-ENG");
                wordnumber=sharedpreferences.getInt("wordnumberkeyenglish", 2);
            }
            else{
                textViewLanguage.setText("Italian");
                speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "it-IT");
                wordnumber=sharedpreferences.getInt("wordnumberkeyitalian", 2);
            }
        }
        int language = extras.getInt("language");
//        wordnumber=2;  // Use when need to zero all scores

//        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        word=nextWord(wordnumber-1,language);
        textViewWord.setText(word);
        textViewWordNumber.setText("Word: "+String.valueOf(wordnumber-1));


//        textViewLanguage.setOnClickListener(new View.OnClickListener() {  /// cheat to delete before production
//            @Override
//            public void onClick(View view) {
//                wordnumber=wordnumber-1;
//                textViewWord.setText(nextWord(wordnumber,language));
//                word=nextWord(wordnumber,language);
//                textViewWordNumber.setText("Word: "+String.valueOf(wordnumber-1));
//
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putInt("wordnumberkey", wordnumber);
//                editor.commit();
//            }
//        });
//
//        textViewWordNumber.setOnClickListener(new View.OnClickListener() { /// cheat to delete before production
//            @Override
//            public void onClick(View view) {
//                wordnumber++;
//                textViewWord.setText(nextWord(wordnumber,language));
//                word=nextWord(wordnumber,language);
//                textViewWordNumber.setText(String.valueOf(wordnumber-1));
//
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putInt("wordnumberkey", wordnumber);
//                editor.commit();
//            }
//        });




        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
//                editText.setText("");
//                editText.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                micButton.setImageResource(R.drawable.ic_mic_black_off);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String resultword=toLowerCase(data.get(0));
                //Exceptions synonyms
                if(resultword.equals("2")){
                    resultword="to";}
                if(resultword.equals("are")){
                    resultword="air";}
                if(resultword.equals("hi")){
                    resultword="high";}
                //check if user said right word
                if(resultword.equals(word)){
                    Toast.makeText(getApplicationContext(), "Nice !", Toast.LENGTH_SHORT).show();
                    textViewWord.setText(nextWord(wordnumber,language));
                    word=nextWord(wordnumber,language);
                    wordnumber++;
                    activatelevels();

                    //save new wordnumber for specific language
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    if(language==1){
                        editor.putInt("wordnumberkeyspanish", wordnumber);
                    }
                    if(language==2){
                        editor.putInt("wordnumberkeyenglish", wordnumber);

                    }
                    if(language==3){
                        editor.putInt("wordnumberkeyitalian", wordnumber);
                    }
                    editor.commit();
                }

                else{
                    Toast.makeText(getApplicationContext(), "Try again... You said "+resultword, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        micButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    speechRecognizer.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    micButton.setImageResource(R.drawable.ic_mic_black_24dp);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        activatelevels();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }

    public String nextWord(int wordnumber,int language){
        InputStream is = getResources().openRawResource(R.raw.wordlist);

        // Reads text from character-input stream, buffering characters for efficient reading
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        // Initialization

        String line = "";
        // Initialization

        try {
            // Step over headers
            reader.readLine();

            for (int i = 0; i < wordnumber+1; i++) {
                line = reader.readLine();  // to get to next line
            }

            String[] tokens = line.split(",");
            return toLowerCase(tokens[language]);  //number to choose column


        } catch (IOException e) {
        }
        return "Failed to get new word...";
    }

    private void activatelevels() {
        int level=(wordnumber-1)/30+1;
        textViewLevel.setText("Lvl "+ level);
        textViewWordNumber.setText("Word: "+String.valueOf(wordnumber-1));
        if((wordnumber-1)%30==0){ //get bounce as level up
            bounceAnim(textViewLevel);
        }
    }

    public void bounceAnim(TextView button){
        ObjectAnimator animY = ObjectAnimator.ofFloat(button, "translationY", -50f, 0f);
        animY.setRepeatMode(ValueAnimator.REVERSE);
        animY.setDuration(200);//1sec
        animY.setInterpolator(new BounceInterpolator());
        animY.setRepeatCount(0);
        animY.start();
    }

}