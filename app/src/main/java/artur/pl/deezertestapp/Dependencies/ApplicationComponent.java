package artur.pl.deezertestapp.Dependencies;

import android.app.Application;

import javax.inject.Singleton;

import artur.pl.deezertestapp.View.MainActivity;
import artur.pl.deezertestapp.View.SearchActivity;
import artur.pl.deezertestapp.View.SearchResultActivity;
import dagger.Component;

/**
 * Created by artur on 10.12.2017.
 */


@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class, RetrofitModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(SearchActivity searchActivity);
    void inject(SearchResultActivity searchResultActivity);

    Application application();
}