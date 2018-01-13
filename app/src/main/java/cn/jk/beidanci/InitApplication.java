package cn.jk.beidanci;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;


/**
 *
 */
public class InitApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        FlowManager.init(this);
    }


}
