package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView textViewVklad = findViewById(R.id.textView);
        TextView textViewUrok = findViewById(R.id.textView2);
        TextView textViewObdobi = findViewById(R.id.textView3);

        SharedPreferences preferences = getSharedPreferences("HistoryPreferences", Context.MODE_PRIVATE);

        float vklad = preferences.getFloat("Vklad", 0.0F);
        float urokovaSazba = preferences.getFloat("UrokovaSazba", 0.0F);
        float dobaUroceni = preferences.getFloat("DobaUroceni", 0.0F);

        textViewVklad.setText("Vklad: " + vklad);
        textViewUrok.setText("Vklad: " + urokovaSazba);
        textViewObdobi.setText("Vklad: " + dobaUroceni);


    }
}