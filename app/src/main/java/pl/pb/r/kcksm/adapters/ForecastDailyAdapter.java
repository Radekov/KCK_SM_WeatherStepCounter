package pl.pb.r.kcksm.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import pl.pb.r.kcksm.model.weather.Weather;

/**
 * Created by Radosław Naruszewicz on 2016-12-01.
 */
//TODO poczytać o ViewPage
public class ForecastDailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Weather> hourWeather;

    public ForecastDailyAdapter(List<Weather> hourWeather) {
        this.hourWeather = hourWeather;
    }

    static class ForecastDailyViewHolder extends RecyclerView.ViewHolder{
        /*dt : int
        * main:
        *   temp : float
        *   tempmin : float
        *   tempmax : float
        *   pressure:float
        *   sealevel: float
        *   grndlevel: float
        *   humidity: int
        *   tempKF: float
        *weather : list : --> inny adapter
        *   id : integer
        *   main : string
        *   description : string
        *   icon : string
        *
        *   */
        TextView image;
        TextView temp;
        TextView hour;

        public ForecastDailyViewHolder(View itemView){
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
