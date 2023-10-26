package com.vsb.kru13.barcodetemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BarcodeView myView;
    Button btnGenerate;
    EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = (BarcodeView) findViewById(R.id.barcodeView);
        etCode = (EditText) findViewById(R.id.etCode);

        btnGenerate = (Button) findViewById(R.id.btnCode);
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String text = etCode.getText().toString();

                if(text.length() == 12)
                {
                    int[] array = new int[12];

                    for (int i = 0; i < 12; i++)
                    {
                        array[i] = text.charAt(i) - '0';
                    }

                    ((BarcodeView)myView).getNum(array);
                    myView.invalidate();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Zadej presne 12 cisel", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
