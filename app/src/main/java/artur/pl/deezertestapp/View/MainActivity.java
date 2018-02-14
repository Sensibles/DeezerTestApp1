package artur.pl.deezertestapp.View;

import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import javax.inject.Inject;

import artur.pl.deezertestapp.Constants;
import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Model.Entity.Track;
import artur.pl.deezertestapp.R;
import artur.pl.deezertestapp.View.Utils.Adapters.ArtistListAdapter;
import artur.pl.deezertestapp.View.Utils.ItemClickListener;
import artur.pl.deezertestapp.View.Utils.Utils;
import artur.pl.deezertestapp.ViewModel.ArtistListViewModel;
import artur.pl.deezertestapp.ViewModel.TestViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static artur.pl.deezertestapp.Constants.ARTIST_FAV;
import static artur.pl.deezertestapp.Constants.ARTIST_ITEM;

public class MainActivity extends BaseActivity implements ItemClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ArtistListViewModel artistListViewModel;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.artistListRecyclerView)
    RecyclerView artistListRecyclerView;

    private ArtistListAdapter artistListAdapter;
    private LiveData<List<Artist>> firstTwentyArtists;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((DeezerTestApp) getApplication())
                .getApplicationComponent()
                .inject(this);
        ButterKnife.bind(this);
        sharedPref = getSharedPreferences(Constants.SP, Context.MODE_PRIVATE);
        setSupportActionBar(myToolbar);
        setupRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_default, menu);
        //For tinting icons
        Utils.tintMenuIcon(MainActivity.this, menu.findItem(R.id.action_favorite), R.color.colorAccent);
        Utils.tintMenuIcon(MainActivity.this, menu.findItem(R.id.search_view), R.color.colorAccent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.search_view:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_favorite:
                intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    protected void onStart() {
        super.onStart();
        artistListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ArtistListViewModel.class);
        setupArtistListResults();
    }


    private void setupArtistListResults(){
        boolean forceNet = sharedPref.getBoolean(Constants.SP_FORCE_NET, false);
        firstTwentyArtists = artistListViewModel.getFirstTwentyArtist(forceNet);
        firstTwentyArtists.observe(this, new Observer<List<Artist>>() {
                @Override
                public void onChanged(@Nullable List<Artist> artists) {
                   MainActivity.this.onChanged(artists);
                }
            });
        }

    public void onChanged(List<Artist> artists){
        if (artists != null) {
            artistListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            artistListAdapter = new ArtistListAdapter(artists, MainActivity.this);
            artistListRecyclerView.setAdapter(artistListAdapter);
            artistListAdapter.notifyDataSetChanged();
        }
    }

    private void setupRecyclerView(){
        artistListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    protected void setCurrentActivity() {
        ((DeezerTestApp) getApplication()).setCurrentActivity(this);

    }

    @Override
    public void onItemClick(int code, Object o) {
        switch(code){
            case ARTIST_FAV:
                if(o instanceof Artist) {
                    Artist artist = (Artist) o;
                    artistListViewModel.updateArtistFav(artist.getId(), !artist.isFavorite());
                }
                break;
            default:
                break;
        }
    }
}
