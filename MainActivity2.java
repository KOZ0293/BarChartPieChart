package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {


    ArrayList barList;
    BarChart barChart;

    PieChart pieChart;

    CardView chartOne;

    CardView chartTwo;

    private ActivityResultLauncher<Intent> grafLauncher;

    private static final int GRAF_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        chartOne = findViewById(R.id.chartOne);
        chartTwo = findViewById(R.id.chartTwo);


        createBarChart();
        createPieChart();

        grafLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String graf = data.getStringExtra("GRAF");
                            //zobrazGraf(graf);
                        }
                    }
                });

        chartOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,ChartApp.class);
                intent.putExtra("GRAF_TYPE", "graf1");
                startActivity(intent);
            }
        });

        chartTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,ChartApp.class);
                intent.putExtra("GRAF_TYPE", "graf2");
                startActivity(intent);
            }
        });


    }

    public void createBarChart(){


        barChart = findViewById(R.id.barchart);
        barList = new ArrayList();
        barList.add(new BarEntry(2f, 20));
        barList.add(new BarEntry(3f, 20));


        BarDataSet barDataSet = new BarDataSet(barList, "Vklad/Úroky");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();



        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);
    }

    public void createPieChart(){


        pieChart = findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(80F,20));
        entries.add(new PieEntry(80F,20));


        PieDataSet pieDataSet = new PieDataSet(entries, "Vklad/Úroky");
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();



        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
       // pieDataSet.setValueTextSize(16f);
         pieChart.getDescription().setEnabled(false);



    }
}