package pl.pb.r.kcksm.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.model.SumStep;
import pl.pb.r.kcksm.services.WheaterService;

/**
 * Created by Radosław Naruszewicz on 2016-11-27.
 */

public class SumStepAdapter extends RecyclerView.Adapter<SumStepAdapter.SumStepViewHolder> {

    private List<SumStep> dataset;

    static class SumStepViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPictureWeather;
        TextView tvDescribeWeather;
        TextView tvSumStepWeather;

        public SumStepViewHolder(View itemView) {
            super(itemView);
            ivPictureWeather = (ImageView)itemView.findViewById(R.id.iv_row_image);
            tvDescribeWeather = (TextView)itemView.findViewById(R.id.tv_row_descritpion);
            tvSumStepWeather = (TextView)itemView.findViewById(R.id.tv_row_step);
        }
    }

    public SumStepAdapter() {
        this.dataset = new ArrayList<>();
    }

    @Override
    public SumStepAdapter.SumStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sum_steps, parent, false);
        return new SumStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SumStepViewHolder holder, int position) {
        SumStep sumStep = dataset.get(position);
        holder.tvDescribeWeather.setText(sumStep.getWeather());
        holder.tvSumStepWeather.setText(Integer.toString(sumStep.getSteps()));
        Picasso.with(holder.ivPictureWeather.getContext())
                .load(String.format(
                        Locale.US,
                        WheaterService.IMG_URL,
                        sumStep.getIco()))
                .into(holder.ivPictureWeather);

        //TODO przemyśleć jak wyświetlać obrazek
//        switch (sumStep.getWeather()){
//            case
//        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setSumSteps(@NotNull List<SumStep> sumSteps){
        dataset = sumSteps;
        //notifyDataSetChanged();
    }
}
