package com.example.jokeractivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokerMainActivity extends AppCompatActivity {
    public static final String JOKE_EXTRA = "joke_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joker_main);
        TextView joke_tv= findViewById(R.id.joke_tv);
        String joke;
        Intent intent=getIntent();
        if(intent!=null&&intent.hasExtra(JOKE_EXTRA)){
            joke = intent.getStringExtra(JOKE_EXTRA);
            joke_tv.setText(joke);

        }
    }
}
