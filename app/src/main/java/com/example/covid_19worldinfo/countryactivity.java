package com.example.covid_19worldinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.widget.Toast;

import com.example.covid_19worldinfo.api.apiutilities;
import com.example.covid_19worldinfo.api.countrydata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class countryactivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<countrydata> list;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countryactivity);

        recyclerView = findViewById(R.id.countries);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);
        countryadapter countryadapter = new countryadapter(this, list);
        recyclerView.setAdapter(countryadapter);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.show();

        apiutilities.getapiinterface().getcountrydata().enqueue(new Callback<List<countrydata>>() {
            @Override
            public void onResponse(Call<List<countrydata>> call, Response<List<countrydata>> response) {
                list.addAll(response.body());

                countryadapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<countrydata>> call, Throwable t) {
                Toast.makeText(countryactivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}