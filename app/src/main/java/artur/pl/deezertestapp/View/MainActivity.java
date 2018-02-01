package artur.pl.deezertestapp.View;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Model.Entity.Track;
import artur.pl.deezertestapp.R;
import artur.pl.deezertestapp.ViewModel.TestViewModel;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    TestViewModel testViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((DeezerTestApp) getApplication())
                .getApplicationComponent()
                .inject(this);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("QUERY", query);
        }
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.search_view) {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("previous_activity", MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("ON START");
        testViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TestViewModel.class);

        //  ARTIST FOR ID
        testViewModel.getArtistForId(2).observe(this, new Observer<Artist>() {
            @Override
            public void onChanged(@Nullable Artist artist) {
                if(artist != null)
                    System.out.printf("ARTIST FOR ID:\n\tID:%d\n\tNAME:%s\n\tPHOTO_URL:%s\n", artist.getId(), artist.getName(), artist.getPhotoUrl());
            }
        });

        //  ARTISTS FOR NAME
        testViewModel.getArtistForName("eminem").observe(this, new Observer<List<Artist>>() {
            @Override
            public void onChanged(@Nullable List<Artist> artists) {
                if(artists != null)
                    for(Artist artist : artists)
                        System.out.printf("ARTIST FOR NAME:\n\tID:%d\n\tNAME:%s\n\tPHOTO_URL:%s\n", artist.getId(), artist.getName(), artist.getPhotoUrl());
            }
        });

        //  ALBUM FOR ID
        testViewModel.getAlbumForId(302127).observe(this, new Observer<Album>() {
            @Override
            public void onChanged(@Nullable Album album) {
                if(album != null)
                    System.out.printf("ALBUM FOR ID:\n\tID:%d\n\tNAME:%s\n\tPHOTO_URL:%s\n", album.getId(), album.getTitle(), album.getCoverUrl());
            }
        });

        testViewModel.getAlbumForArtistId(13).observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable List<Album> albums) {
                if(albums != null) {
                    int counter = 0;
                    for (Album album : albums) {
                        System.out.printf("[%d]ALBUM FOR ARTIST ID:\n\tID:%d\n\tNAME:%s\n\tPHOTO_URL:%s\n", counter, album.getId(), album.getTitle(), album.getCoverUrl());
                        counter++;
                    }
                }
            }
        });

        // TRACK

        testViewModel.getTrackForId(3135556).observe(this, new Observer<Track>() {
            @Override
            public void onChanged(@Nullable Track track) {
                if(track != null)
                        System.out.printf("TRACK FOR ID:\n\tID:%d\n\tTITLE:%s\n\tPREVIEW_URL:%s\n",  track.getId(), track.getTitle(), track.getPreviewUrl());
            }
        });


        testViewModel.getTrackForAlbumId(302127).observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable List<Track> tracks) {
                if(tracks != null) {
                    int counter = 0;
                    for (Track track : tracks) {
                        System.out.printf("[%d]TRACK FOR ALBUM ID:\n\tID:%d\n\tTITLE:%s\n\tPREVIEW_URL:%s\n",counter, track.getId(), track.getTitle(), track.getPreviewUrl());
                        counter++;
                    }
                }
            }
        });

        testViewModel.getTop5TracksForArtistId(13).observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable List<Track> tracks) {
                if(tracks != null) {
                    int counter = 0;
                    for (Track track : tracks) {
                        System.out.printf("[%d]TOP 5 TRACKS FOR ARTIST ID:\n\tID:%d\n\tTITLE:%s\n\tPREVIEW_URL:%s\n",counter, track.getId(), track.getTitle(), track.getPreviewUrl());
                        counter++;
                    }
                }
            }
        });

    }


    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    protected void setCurrentActivity() {
        ((DeezerTestApp) getApplication()).setCurrentActivity(this);

    }
}
