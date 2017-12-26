package artur.pl.deezertestapp;

import android.app.Activity;
import android.app.Application;

import artur.pl.deezertestapp.Dependencies.ApplicationComponent;
import artur.pl.deezertestapp.Dependencies.ApplicationModule;
import artur.pl.deezertestapp.Dependencies.DaggerApplicationComponent;
import artur.pl.deezertestapp.Dependencies.RetrofitModule;
import artur.pl.deezertestapp.Dependencies.RoomModule;

/**
 * Created by artur on 09.12.2017.
 */

public class DeezerTestApp extends Application {


    private ApplicationComponent applicationComponent;
    private Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .retrofitModule(new RetrofitModule(
                        "https://api.deezer.com/"))
                .roomModule(new RoomModule(this))
                .build();


    }



    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }
}
