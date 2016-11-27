package pl.pb.r.kcksm;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import pl.pb.r.kcksm.model.DaoMaster;
import pl.pb.r.kcksm.model.DaoSession;

/**
 * Created by Radosław Naruszewicz on 2016-11-27.
 */

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "sumstep-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
