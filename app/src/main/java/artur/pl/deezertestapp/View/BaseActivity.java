package artur.pl.deezertestapp.View;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by artur on 27.11.2017.
 */
//TODO: TRY TO REPLACE IT WITH INTERFACE INSTEAD OF ABSTRACT CLASS
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentActivity();

    }

    protected abstract void setCurrentActivity();
}
