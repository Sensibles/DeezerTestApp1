package artur.pl.deezertestapp.View;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import artur.pl.deezertestapp.Constants;
import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.R;
import artur.pl.deezertestapp.View.Utils.Adapters.ArtistListAdapter;
import artur.pl.deezertestapp.View.Utils.ItemClickListener;
import artur.pl.deezertestapp.ViewModel.ArtistListViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import static artur.pl.deezertestapp.Constants.ARTIST_FAV;
import static artur.pl.deezertestapp.Constants.ARTIST_ITEM;

public class FavoriteActivity extends BaseActivity implements ItemClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ArtistListViewModel artistListViewModel;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.artistListRecyclerView)
    RecyclerView artistListRecyclerView;

    private ArtistListAdapter artistListAdapter;
    private LiveData<List<Artist>> favoriteArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setContentView(R.layout.activity_main);
        ((DeezerTestApp) getApplication())
                .getApplicationComponent()
                .inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        setupRecyclerView();
        getSupportActionBar().setTitle(getText(R.string.action_favorite));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        Artist artist = (Artist) o;
        switch(code){
            case ARTIST_FAV:
                if(o instanceof Artist) {
                    artistListViewModel.updateArtistFav(artist.getId(), !artist.isFavorite());
                }
                break;
            case ARTIST_ITEM:
                if(o instanceof Artist) {
                    Intent intent = new Intent(this, ArtistDetailActivity.class);
                    intent.putExtra(Constants.ARTIST_INTENT, artist.getId());
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    private void setupArtistListResults(){
        favoriteArtists = artistListViewModel.getFavoriteArtists();
        favoriteArtists.observe(this, new Observer<List<Artist>>() {
            @Override
            public void onChanged(@Nullable List<Artist> artists) {
                FavoriteActivity.this.onChanged(artists);
            }
        });
    }

    public void onChanged(List<Artist> artists){
        if (artists != null) {
            if(artistListRecyclerView.getAdapter() == null){
                artistListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                artistListAdapter = new ArtistListAdapter(artists, FavoriteActivity.this);
                artistListRecyclerView.setAdapter(artistListAdapter);
            }
            else if(artistListAdapter != null)
                artistListAdapter.setArtistsItemList(artists);
            artistListAdapter.notifyDataSetChanged();
        }
    }

    private void setupRecyclerView(){
        artistListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }


}
