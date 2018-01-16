package me.mjaroszewicz.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 1/16/18.
 */

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastViewHolder> {

    private LayoutInflater layoutInflater;

    private List<Weather> data;

    private Context context;

    ForecastRecyclerAdapter(Context context, List<Weather> data) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.forecast_recycler_item, parent, false);
        ForecastViewHolder holder = new ForecastViewHolder(v);

        return holder;
    }

    //binding data to the view
    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {

        Weather w = data.get(position);

        //weather icon
        Resources r = context.getResources();
        String iconName = "ic_" + w.getIcon();
        int iconId = r.getIdentifier(iconName, "drawable", context.getPackageName());
        holder.iconImageView.setImageDrawable(r.getDrawable(iconId));

        //time
        long time = w.getTime();
        Date date = new Date(time * 1000);
        String timeString = date.getHours() + ":00";
        holder.timeTextView.setText(timeString);

        Log.w("DATETIME", time + " ");

        //temperature
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String temperatureScale = sharedPreferences.getString("temperature_scale", "celsius");
        String tempString = Utils.getTemperatureString(w, temperatureScale);
        holder.temperatureTextView.setText(tempString);


    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}

class ForecastViewHolder extends RecyclerView.ViewHolder{

    View mView;


    ImageView iconImageView;
    TextView temperatureTextView;
    TextView timeTextView;

    ForecastViewHolder(View view) {

        super(view);

        iconImageView = view.findViewById(R.id.forecast_recycler_icon);
        temperatureTextView = view.findViewById(R.id.forecast_recycler_temp);
        timeTextView = view.findViewById(R.id.forecast_recycler_time);



    }

}

