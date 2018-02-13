package artur.pl.deezertestapp.View;

import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.R;
import artur.pl.deezertestapp.View.Utils.Adapters.ArtistListAdapter;
import artur.pl.deezertestapp.View.Utils.ItemClickListener;
import artur.pl.deezertestapp.ViewModel.ArtistListViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import static artur.pl.deezertestapp.Constants.ARTIST_FAV;

public class SearchResultActivity extends BaseActivity implements ItemClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ArtistListViewModel artistListViewModel;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.artistListRecyclerView)
    RecyclerView artistListRecyclerView;

    private ArtistListAdapter artistListAdapter;
    private String query;
    private LiveData<List<Artist>> artistsByName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ((DeezerTestApp) getApplication())
                .getApplicationComponent()
                .inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        setupRecyclerView();
        getSearchQuery();
    }

    @Override
    protected void onStart() {
        super.onStart();
        artistListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ArtistListViewModel.class);
        setupArtistListResults();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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

    private void setupRecyclerView(){
        artistListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }


    private boolean getSearchQuery(){
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            this.query = query;
            Log.d("QUERY", query);
            return true;
        }
        return false;
    }

    public void onChanged(List<Artist> artists){
        if (artists != null) {
            artistListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            artistListAdapter = new ArtistListAdapter(artists, SearchResultActivity.this);
            artistListRecyclerView.setAdapter(artistListAdapter);
            artistListAdapter.notifyDataSetChanged();
        }
    }

    private void setupArtistListResults(){
            getSupportActionBar().setTitle("'"+query+"'");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            artistsByName = artistListViewModel.getArtistsForName(query, true);
            artistsByName.observe(this, new Observer<List<Artist>>() {
                @Override
                public void onChanged(@Nullable List<Artist> artists) {
                    SearchResultActivity.this.onChanged(artists);
                }
            });
    }
}
