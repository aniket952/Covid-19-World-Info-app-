package com.example.covid_19worldinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid_19worldinfo.api.countrydata;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class countryadapter extends RecyclerView.Adapter<countryadapter.Countryviewholder> {
    private Context context;
    private List<countrydata> list;

    public countryadapter(Context context, List<countrydata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Countryviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item_layout, parent, false);
        return new Countryviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Countryviewholder holder, int position) {
        countrydata data = list.get(position);
        holder.Countrycases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
        holder.Countryname.setText(data.getCountry());
        holder.srN.setText(String.valueOf(position+1));

        Map<String, String> img = data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.flag);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Countryviewholder extends RecyclerView.ViewHolder {
        private TextView srN  ,Countryname , Countrycases;
        private ImageView flag;
        public Countryviewholder(@NonNull View itemView) {
            super(itemView);
            srN = itemView.findViewById(R.id.srN);
            Countryname = itemView.findViewById(R.id.Countryname);
            Countrycases= itemView.findViewById(R.id.Countrycases);
            flag = itemView.findViewById(R.id.flag);

        }
    }

}
