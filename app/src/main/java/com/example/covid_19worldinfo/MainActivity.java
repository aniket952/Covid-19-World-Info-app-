package com.example.covid_19worldinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.slice.Slice;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19worldinfo.api.apiinterface;
import com.example.covid_19worldinfo.api.apiutilities;
import com.example.covid_19worldinfo.api.countrydata;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView totalconfirm, totalactive, totalrecovered, totaldeath, totaltest;
    private TextView todayconfirm, todayrecoverred, todaydeath, date;
    private PieChart piechart;
    private List<countrydata> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        init();
        findViewById(R.id.cname).setOnClickListener(V -> startActivity(new Intent(MainActivity.this, countryactivity.class)));
        apiutilities.getapiinterface().getcountrydata()
                .enqueue(new Callback<List<countrydata>>() {
                    @Override
                    public void onResponse(Call<List<countrydata>> call, Response<List<countrydata>> response) {
                        list.addAll(response.body());

                        for(int i=0;i<list.size(); i++){
                            if(list.get(i).getCountry().equals("India")){
                                int confirm = Integer.parseInt(list.get(i).getCases());
                                int active = Integer.parseInt(list.get(i).getActive());
                                int recovered = Integer.parseInt(list.get(i).getRecovered());
                                int death = Integer.parseInt(list.get(i).getDeaths());

                                totalconfirm.setText(NumberFormat.getInstance().format(confirm));
                                totalactive.setText(NumberFormat.getInstance().format(active));
                                totalrecovered.setText(NumberFormat.getInstance().format(recovered));
                                totaldeath.setText(NumberFormat.getInstance().format(death));
                                totaltest.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));



                                todaydeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                                todayconfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                                todayrecoverred.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));




                                piechart.addPieSlice(new PieModel("confirm", confirm, getResources().getColor(R.color.yellow)));
                                piechart.addPieSlice(new PieModel("active", active, getResources().getColor(R.color.blue)));
                                piechart.addPieSlice(new PieModel("recovered", recovered, getResources().getColor(R.color.green)));
                                piechart.addPieSlice(new PieModel("death", death, getResources().getColor(R.color.design_default_color_error)));

                                piechart.startAnimation();
                                
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<countrydata>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void init(){
        totalconfirm = findViewById(R.id.totalconfirm);
        totalactive = findViewById(R.id.totalactive);
        totalrecovered = findViewById(R.id.totalrecovred);
        totaldeath = findViewById(R.id.totaldeath);
        totaltest = findViewById(R.id.totaltest);
        todayconfirm = findViewById(R.id.todayconfirm);
        todayrecoverred = findViewById(R.id.todayrecovred);
        todaydeath = findViewById(R.id.todaydeath);
        piechart = findViewById(R.id.piechart);
        totaltest = findViewById(R.id.totaltest);
        date = findViewById(R.id.date);

    }

}