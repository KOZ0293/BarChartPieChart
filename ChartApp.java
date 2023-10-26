package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class ChartApp extends AppCompatActivity {
    ArrayList barList;
    BarChart barChart;
    PieChart pieChart;
    TextView nasporenaSuma;
    TextView uroky;

    TextView sazba;
    SeekBar vklad;
    SeekBar urok;
    SeekBar obdobi;

    float obdobiVal = 1;
    float urokVal = 1;

    float vlozenePenize = 50000;
    float urokSazba = 0;
    float interest = 0;

    float A = 0;

    TextView vlozeni;

    TextView time;

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nasporenaSuma = (TextView) findViewById(R.id.nasporenaSuma);
        vlozeni = (TextView) findViewById(R.id.vlozeni);
        uroky = (TextView) findViewById(R.id.uroky);
        time = (TextView) findViewById(R.id.time);
        sazba = (TextView) findViewById(R.id.sazba);
        pieChart = findViewById(R.id.piechart);
        barChart = findViewById(R.id.barchart);


        readPreferences();
        createPieChart();
        createBarChart();






        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("GRAF_TYPE")) {
            String grafType = intent.getStringExtra("GRAF_TYPE");

            // Zde můžete provést aktualizaci grafu podle zvoleného typu
            if (grafType.equals("graf1")) {

                createPieChart();
            } else if (grafType.equals("graf2")) {
                createBarChart();
            }
        }




        vklad = (SeekBar) findViewById(R.id.vklad);
        vklad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                vlozenePenize = progress;
                vlozeni.setText("Vložení: " + vlozenePenize);


                updateValues();
                updateBarChart();
                updatePieChart();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        urok = (SeekBar) findViewById(R.id.urok);
        urok.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                urokVal = progress;
                sazba.setText("Úrok: " + urokVal);


                updateValues();
                updateBarChart();
                updatePieChart();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {



            }
        });

        obdobi = (SeekBar) findViewById(R.id.obdobi);
        obdobi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                obdobiVal = progress;
                time.setText("Období: " + obdobiVal);


                updateValues();
                updateBarChart();
                updatePieChart();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.actionChartSelection) {
            openChartSelection();
            return true;
        } else if (id == R.id.actionHistory) {
            openHistory();
            return true;
        }

        else if (id == R.id.actionHelp) {
            openHelp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openChartSelection() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void openHistory() {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    public void openHelp() {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }


    public void createBarChart(){

        pieChart.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);

        barList = new ArrayList();
        barList.add(new BarEntry(2f,  preferences.getFloat("Vklad", 0)));
        barList.add(new BarEntry(3f, preferences.getFloat("Urok", 0)));


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

        barChart.setVisibility(View.GONE);

        pieChart.setVisibility(View.VISIBLE);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(preferences.getFloat("Vklad", 0),"Vklad"));
        entries.add(new PieEntry(preferences.getFloat("Urok", 0),"Úroky"));


        PieDataSet pieDataSet = new PieDataSet(entries, "Vklad/Úroky");
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();



        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        // pieDataSet.setValueTextSize(16f);
        pieChart.getDescription().setEnabled(false);



    }

    public void updateBarChart(){
       // pieChart.setVisibility(View.GONE);
        savePreferences( vlozenePenize, interest, obdobiVal);
        updateValues();
        barChart = findViewById(R.id.barchart);
        barList = new ArrayList();
        barList.add(new BarEntry(2f, vlozenePenize));
        barList.add(new BarEntry(3f,  interest));

        BarDataSet barDataSet = new BarDataSet(barList, "Vklad/Úroky");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        //barChart.getDescription().setEnabled(true);
    }

    public void updatePieChart(){
       // barChart.setVisibility(View.GONE);
        savePreferences( vlozenePenize, interest, obdobiVal);
        updateValues();
        pieChart = findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(vlozenePenize,"Vklad"));
        entries.add(new PieEntry(interest,"Úroky"));


        PieDataSet pieDataSet = new PieDataSet(entries, "Vklad/Úroky");
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();



        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        // pieDataSet.setValueTextSize(16f);
        pieChart.getDescription().setEnabled(false);
    }


    private void updateValues() {
        A = (float) (vlozenePenize * Math.pow(1 + ((urokVal/100) / 1), 1 * obdobiVal));

       interest = A - vlozenePenize;

        nasporenaSuma.setText("Naspořená suma: " + A);
        uroky.setText("Z toho úroky: " + interest);
        savePreferences( A, interest);

    }

    private void savePreferences(float v,float u,float d){


            // Storing data into SharedPreferences
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HistoryPreferences",MODE_PRIVATE);
            // Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor historyEditor = sharedPreferences.edit();
           // historyEditor.putString("history", data);
        historyEditor.putFloat("Vklad", (float) vlozenePenize);
        historyEditor.putFloat("UrokovaSazba", (float) urokVal);
        historyEditor.putFloat("DobaUroceni", (float) obdobiVal);


        historyEditor.apply();
            Log.w("APP", "Writing '" + v +" "+ u + " " + d + " " +" to preferences");


    }

    private void savePreferences(float v,float u){


        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HistoryPreferences",MODE_PRIVATE);
        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor historyEditor = sharedPreferences.edit();
        // historyEditor.putString("history", data);

        historyEditor.putFloat("NasporenaSuma", (float) A);
        historyEditor.putFloat("Urok", (float) interest);


        historyEditor.apply();
        Log.w("APP", "Writing '" + v +" "+ u + " " + " " +" to preferences");


    }

    private void readPreferences()
    {
         preferences = getSharedPreferences("HistoryPreferences", Context.MODE_PRIVATE);

        float v = preferences.getFloat("Vklad", 0);
        float u = preferences.getFloat("UrokovaSazba", 0);
        float d = preferences.getFloat("DobaUroceni", 0);
        float s = preferences.getFloat("NasporenaSuma", 0);
        float t = preferences.getFloat("Urok", 0);


        vlozeni.setText("Vklad: " + v);
        sazba.setText("Úrok: " + u);
        time.setText("Období " + d);
        nasporenaSuma.setText("Naspořená suma: " + s);
        uroky.setText("Z toho úroky: " + t);

       /* vklad.setProgress((int) v);
        urok.setProgress((int) u);
        obdobi.setProgress((int) v);*/

       //  vlozenePenize = preferences.getFloat("Vklad", 0.0F);
        // urokVal = preferences.getFloat("UrokovaSazba", 0.0F);
         //obdobiVal = preferences.getFloat("DobaUroceni", 0.0F);
    }




}












