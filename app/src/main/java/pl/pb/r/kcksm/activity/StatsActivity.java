package pl.pb.r.kcksm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import pl.pb.r.kcksm.App;
import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.adapters.SumStepAdapter;
import pl.pb.r.kcksm.model.DaoSession;
import pl.pb.r.kcksm.model.SumStep;
import pl.pb.r.kcksm.model.SumStepDao;
import pl.pb.r.kcksm.services.SumStepsDaoService;

public class StatsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SumStepAdapter sumStepAdapter;
    private SumStepDao sumStepDao;

    private TextView sumStepTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        setUpViews();

        DaoSession daoSession = ((App)getApplication()).getDaoSession();
        sumStepDao = daoSession.getSumStepDao();
        List<SumStep> data = sumStepDao.loadAll();
        sumStepAdapter.setSumSteps(data);

    }
    private void setUpViews(){
        recyclerView = (RecyclerView)findViewById(R.id.stats_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sumStepAdapter = new SumStepAdapter();
        recyclerView.setAdapter(sumStepAdapter);

        sumStepTV = (TextView)findViewById(R.id.tv_sum_steps);
        try {
            sumStepTV.setText(SumStepsDaoService.getInstance().sumAllSteps());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
