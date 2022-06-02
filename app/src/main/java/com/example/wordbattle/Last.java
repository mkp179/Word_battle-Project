package com.example.wordbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Last extends AppCompatActivity {
    private int presCounter = 0;
    private int maxPresCounter = 8;
    public int o=0;
    public static int p=0;
    private String[] keys = {"A", "W", "F", "D", "E","F","G","H","I","L","M","O","R","S","T"};
    private String[] textAnswer = {"GIRAFFE",
            "WHALE",
            "GOLDFISH" ,
            "HAMSTER"};
    TextView textScreen, textQuestion, textTitle;
    public String []word;
    public String wor;
    public LinearLayout linearLayout;
    Animation smallbigforth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        keys = shuffleArray(keys);
        word=shuffleArray(textAnswer);
        wor=word[0];
        for (String key : keys) {
            if (o<6) {
                addView(((LinearLayout) findViewById(R.id.layoutParent)), key, ((EditText) findViewById(R.id.editText)));
                o++;
            }
            if (o>=6 && o<11){
                addView(((LinearLayout) findViewById(R.id.layoutParent2)), key, ((EditText) findViewById(R.id.editText)));
                o++;
            }
            if (o>=11 && o<=16){
                addView(((LinearLayout) findViewById(R.id.layoutParent3)), key, ((EditText) findViewById(R.id.editText)));
                o++;
            }

        }

        maxPresCounter = wor.length();
        textQuestion.setText("Guess Animals Name - "+String.valueOf(maxPresCounter));

    }
    private String[] shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }


    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayoutParams.rightMargin = 30;



        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.colorPurple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textScreen = (TextView) findViewById(R.id.textScreen);
        textTitle = (TextView) findViewById(R.id.textTitle);

        textQuestion.setTypeface(typeface);
        textScreen.setTypeface(typeface);
        textTitle.setTypeface(typeface);
        editText.setTypeface(typeface);
        textView.setTypeface(typeface);

        textView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(presCounter < maxPresCounter) {
                    if (presCounter == 0)
                        editText.setText("");

                    editText.setText(editText.getText().toString() + text);
                    textView.startAnimation(smallbigforth);
                    textView.animate().alpha(0).setDuration(300);
                    presCounter++;

                    if (presCounter == maxPresCounter)
                        doValidate();
                }
            }
        });


        viewParent.addView(textView);


    }


    private void doValidate() {
        presCounter = 0;
        p=1;
        EditText editText = findViewById(R.id.editText);

        if (p<6) {
            linearLayout = findViewById(R.id.layoutParent);
            p++;
        }
        if (p>=6 && p<11){
            linearLayout = findViewById(R.id.layoutParent2);
            p++;
        }
        if (p>=11 && p<=16){
            linearLayout = findViewById(R.id.layoutParent3);
            p++;
        }


        if(editText.getText().toString().equals(wor)) {
           Toast.makeText(this, "Correct Answer ", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), start.class);
            startActivity(i);
            finish();
            editText.setText("");

        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            editText.setText("");

        }

        keys = shuffleArray(keys);
        linearLayout.removeAllViews();
        for (String key : keys) {
            addView(linearLayout, key, editText);
        }

    }
}