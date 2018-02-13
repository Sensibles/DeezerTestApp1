package artur.pl.deezertestapp.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import artur.pl.deezertestapp.Constants;
import artur.pl.deezertestapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.settingsName1TextView)
    TextView name1TextView;

    @BindView(R.id.settingsDesc1TextView)
    TextView desc1TextView;

    @BindView(R.id.setting1Switch)
    Switch setting1Switch;

    @BindView(R.id.settingsName2TextView)
    TextView name2TextView;

    @BindView(R.id.settingsDesc2TextView)
    TextView desc2TextView;

    @BindView(R.id.setting2Switch)
    Switch setting2Switch;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    private boolean forceNet, enableCache;
    private  SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getText(R.string.action_settings));
   //     http://d0od.wpengine.netdna-cdn.com/wp-content/uploads/2014/12/material-chrome-settings.jpg
        sharedPref = getSharedPreferences(Constants.SP, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        getPreferences();
        setSettingsLabels();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void getPreferences(){

        forceNet = sharedPref.getBoolean(Constants.SP_FORCE_NET, false);
        enableCache = sharedPref.getBoolean(Constants.SP_ENABLE_CACHE, false);
    }

    private void setSettingsLabels(){
        name1TextView.setText(getText(R.string.setting_name_force_net));
        desc1TextView.setText(getText(R.string.setting_desc_force_net));
        setting1Switch.setChecked(forceNet);

        name2TextView.setText(getText(R.string.setting_name_cashe_pics));
        desc2TextView.setText(getText(R.string.setting_desc_cashe_pics));
        setting2Switch.setChecked(enableCache);
    }

    @OnClick(R.id.setting1Switch)
    public void onSetting1Switch(View v){
        editor.putBoolean(Constants.SP_FORCE_NET, setting1Switch.isChecked());
        editor.commit();
    }

    @OnClick(R.id.setting2Switch)
    public void onSetting2Switch(View v){
        editor.putBoolean(Constants.SP_ENABLE_CACHE, setting2Switch.isChecked());
        editor.commit();
    }


}
