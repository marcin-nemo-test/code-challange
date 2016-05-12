package tk.zielony.codechallange;

import android.app.Application;

import tk.zielony.codechallange.api.DataAPI;
import tk.zielony.codechallange.api.LocalAPI;

/**
 * Created by Marcin on 2016-05-12.
 */
public class CCApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataAPI.init(new LocalAPI());
    }
}
