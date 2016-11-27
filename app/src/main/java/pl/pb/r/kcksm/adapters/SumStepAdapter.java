package pl.pb.r.kcksm.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.model.SumStep;

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

    @Override
    public SumStepAdapter.SumStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sum_steps, parent, false);
        return new SumStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SumStepViewHolder holder, int position) {
        SumStep sumStep = dataset.get(position);
        holder.tvSumStepWeather.setText(sumStep.getWeather());
        holder.tvSumStepWeather.setText(sumStep.getSteps());

        //TODO przemyśleć jak wyświetlać obrazek
//        switch (sumStep.getWeather()){
//            case
//        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
