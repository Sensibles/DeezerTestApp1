package artur.pl.deezertestapp.View;

import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import artur.pl.deezertestapp.Constants;
import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.R;
import artur.pl.deezertestapp.View.Utils.Adapters.ArtistListAdapter;
import artur.pl.deezertestapp.View.Utils.Utils;
import artur.pl.deezertestapp.ViewModel.ArtistDetailsViewModel;
import artur.pl.deezertestapp.ViewModel.ArtistListViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistDetailActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ArtistDetailsViewModel artistDetailsViewModel;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.noAlbumsTextView)
    TextView noAlbumsTextView;

    @BindView(R.id.noFansTextView)
    TextView noFansTextView;

    @BindView(R.id.bgImageView)
    ImageView bgImageView;

    private int artistId;
    private boolean fav;
    private Menu menu;
    private LiveData<Artist> artistDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);
        ((DeezerTestApp) getApplication())
                .getApplicationComponent()
                .inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getArtistId();
    }

    @Override
    protected void onStart() {
        super.onStart();
        artistDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ArtistDetailsViewModel.class);
        observeArtist();
    //    setupArtistListResults();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_artist, menu);
        if(fav)
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_black_24dp);
        //For tinting icons
        Utils.tintMenuIcon(ArtistDetailActivity.this, menu.findItem(R.id.action_favorite), R.color.colorAccent);
        return true;
    }

    @Override
    protected void setCurrentActivity() {
        ((DeezerTestApp) getApplication()).setCurrentActivity(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_favorite:
               artistDetailsViewModel.updateArtistFav(artistId,!fav);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getArtistId(){
        Intent intent = getIntent();
        artistId = intent.getIntExtra(Constants.ARTIST_INTENT, 0);
    }

    private void observeArtist(){
        artistDetails = artistDetailsViewModel.getArtistForId(artistId, false);
        artistDetails.observe(this, new Observer<Artist>() {
            @Override
            public void onChanged(@Nullable Artist artist) {
                ArtistDetailActivity.this.onChanged(artist);
            }
        });
    }

    public void onChanged(Artist artist){
        if (artist != null) {
           setArtistInfo(artist);
            setFavIcon();
        }
    }

    private void setArtistInfo(Artist artist){
        DecimalFormat myFormatter = new DecimalFormat("###,###,###");
        String formatedNoFans = myFormatter.format(artist.getNumberOfFans());
        getSupportActionBar().setTitle(artist.getName());
        noAlbumsTextView.setText(getString(R.string.tv_no_albums) +" " +artist.getNumberOfAlbums());
        noFansTextView.setText(getString(R.string.tv_no_fans)+" "+formatedNoFans);

        Glide.with(this)
                .load(artist.getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .centerInside())
                .into(bgImageView);

        this.fav = artist.isFavorite();


    }

    private void setFavIcon(){
         /*
        On first run of this method, menu object is not created because onStart method is called before onCreateOptionsMenu.
        That's why im assigning artist fav value to local fav variable, to allow onCreateOptionsMenu set proper fav icon.
        After onCreateOptionsMenu is called, it won't be called anymore, so we have to handle icon change by ourselves.
         */
        if(menu != null){
            if(fav)
                menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_black_24dp);
            else
                menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_border_black_24dp);
            Utils.tintMenuIcon(ArtistDetailActivity.this, menu.findItem(R.id.action_favorite), R.color.colorAccent);
        }
    }


}
