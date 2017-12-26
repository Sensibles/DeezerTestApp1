package artur.pl.deezertestapp.Dependencies;

import android.app.Application;

import artur.pl.deezertestapp.DeezerTestApp;
import dagger.Module;
import dagger.Provides;

/**
 * Created by artur on 10.12.2017.
 */

@Module
public class ApplicationModule {
    private final DeezerTestApp androidApplication;

    public ApplicationModule(DeezerTestApp androidApplication) {
        this.androidApplication = androidApplication;
    }

    @Provides
    DeezerTestApp providesAndroidApplication(){
        return androidApplication;
    }

    @Provides
    Application providesApplication(){
        return androidApplication;
    }
}
