package pl.pb.r.kcksm.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        sumStepDao = daoSession.getSumStepDao();
        List<SumStep> data = sumStepDao.loadAll();
        sumStepAdapter.setSumSteps(data);

    }

    private void setUpViews() {
        recyclerView = (RecyclerView) findViewById(R.id.stats_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sumStepAdapter = new SumStepAdapter();
        recyclerView.setAdapter(sumStepAdapter);

        sumStepTV = (TextView) findViewById(R.id.tv_sum_steps);
        try {
            sumStepTV.setText(SumStepsDaoService.getInstance().sumAllSteps());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteData(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage(R.string.alert_description)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (sumStepAdapter.getItemCount() > 0)
                            SumStepsDaoService.getInstance().deleteAll();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
