package artur.pl.deezertestapp.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.OnConflictStrategy;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Dao.ArtistDao;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Repository.Utils.ProgressDialogAsyncTask;
import artur.pl.deezertestapp.Rest.ArtistClient;
import artur.pl.deezertestapp.Rest.ResponseObject.ArtistResponseObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by artur on 10.12.2017.
 */

public class ArtistRepository {

    private ArtistDao artistDao;
    private ArtistClient client;
    private Application application;

    @Inject
    public ArtistRepository(ArtistDao artistDao, ArtistClient client, Application application) {
        this.artistDao = artistDao;
        this.client = client;
        this.application = application;
    }

    public LiveData<List<Artist>> getFavoriteArtists(){
        return artistDao.getFavoriteArtists();
    }

    //Mediators between db source and WebServices
    public MediatorLiveData<List<Artist>> getArtistForIdBetween(final int idStart, final int idStop, final boolean forceOverride){
        final MediatorLiveData<List<Artist>> artistMediatorLiveData = new MediatorLiveData<>();
        final LiveData<List<Artist>> dbSource = artistDao.getArtistForIdBetween(idStart, idStop);
        artistMediatorLiveData.addSource(dbSource, new Observer<List<Artist>>() {

            @Override
            public void onChanged(@Nullable List<Artist> artists) {
               if(artists.isEmpty() || forceOverride) {
                   artistMediatorLiveData.removeSource(dbSource);
                   for(int id = idStart; id <=idStop; id++) {
                       getArtistForIdFromWS(id);
                   }
                   artistMediatorLiveData.addSource(dbSource, new Observer<List<Artist>>() {
                       @Override
                       public void onChanged(@Nullable List<Artist> value) {
                           artistMediatorLiveData.setValue(value);
                       }
                   });
               }
               else {
                   artistMediatorLiveData.setValue(artists);
               }
            }
        });
        return artistMediatorLiveData;
    }

    public MediatorLiveData<List<Artist>> getArtistsForName(final String name,final boolean forceOverride){
        final MediatorLiveData<List<Artist>> artistMediatorLiveData = new MediatorLiveData<>();
        final LiveData<List<Artist>> dbSource = artistDao.getArtistForName(name);
        artistMediatorLiveData.addSource(dbSource, new Observer<List<Artist>>() {

            @Override
            public void onChanged(@Nullable List<Artist> artists) {
                if(artists.isEmpty() || forceOverride) {
                    artistMediatorLiveData.removeSource(dbSource);
                    getArtistForNameFromWS(name);
                    artistMediatorLiveData.addSource(dbSource, new Observer<List<Artist>>() {
                        @Override
                        public void onChanged(@Nullable List<Artist> value) {
                            artistMediatorLiveData.setValue(value);
                        }
                    });
                }
                else {
                    artistMediatorLiveData.setValue(artists);
                }
            }
        });
        return artistMediatorLiveData;
    }

    //WebServices
    public void getArtistForIdFromWS(int id){
        Call<ArtistResponseObject> call = client.getArtistForId(String.valueOf(id));
        call.enqueue(new Callback<ArtistResponseObject>() {
                @Override
                public void onResponse(Call<ArtistResponseObject> call, Response<ArtistResponseObject> response) {
                    if (response.body() == null)
                        Log.e("RETROFIT", response.message());
                    else {
                        Artist artist = response.body().getList().get(0);
                        if (artist != null)
                            new AddArtist((DeezerTestApp) application,
                                    "Synchronizacja artystów").execute(artist);
                    }
                }

                @Override
                public void onFailure(Call<ArtistResponseObject> call, Throwable t) {
                    Log.e("RETROFIT", "RETROFIT JUST FUCKED UP: " + call.toString());
                    t.printStackTrace();
                }
        });
    }

    public void getArtistForNameFromWS(String name){
        Call<ArtistResponseObject> call = client.getArtistForName(name);
        call.enqueue(new Callback<ArtistResponseObject>() {
            @Override
            public void onResponse(Call<ArtistResponseObject> call, Response<ArtistResponseObject> response) {

                if(response.body() == null) {
                    Log.e("RETROFIT", response.raw().toString());
                }
                else {
                    List<Artist> artists= response.body().getList();
                    if(artists != null)
                        new AddListOfArtists((DeezerTestApp)application,
                                "Synchronizacja artystów").execute(artists);
                }
            }
            @Override
            public void onFailure(Call<ArtistResponseObject> call, Throwable t) {
                Log.e("RETROFIT", "RETROFIT JUST FUCKED UP");
                t.printStackTrace();
            }
        });
    }

    //Async tasks
    private class AddListOfArtists extends ProgressDialogAsyncTask<List<Artist>, Void, Void> {
        public AddListOfArtists(DeezerTestApp application, String msgText) {
            super(application, msgText);
        }
        @Override
        protected Void doInBackground(List<Artist>... artists){
            for(Artist artist : artists[0])
                createNewArtist(artist);
            return null;
        }
    }

    public void updateFavorite(int id, boolean fav){
        new UpdateFav((DeezerTestApp) application, "Synchronizacja artystów").execute(new Pair<Integer, Boolean>(id, fav));
    }

    public void insertOrUpdateArtist(Artist artist){
        new AddArtist((DeezerTestApp) application, "Synchronizacja artystów").execute(artist);
    }


    private class AddArtist extends ProgressDialogAsyncTask<Artist, Void, Void> {
        public AddArtist(DeezerTestApp application, String msgText) {
            super(application, msgText);
        }
        @Override
        protected Void doInBackground(Artist... artists){
            createNewArtist(artists[0]);
            return null;
        }
    }

    private class UpdateFav extends ProgressDialogAsyncTask<Pair<Integer, Boolean>, Void, Void> {
        public UpdateFav(DeezerTestApp application, String msgText) {
            super(application, msgText);
        }
        @Override
        protected Void doInBackground(Pair<Integer, Boolean>... pair){
            updateFav(pair[0].first, pair[0].second);
            return null;
        }
    }

    //Other

    private long createNewArtist(Artist artist){
        Log.d("ARTIST", "createNewArtist: " + artist.getId() + ", "+ artist.getName());
      if(artistDao.insertArtist(artist) == OnConflictStrategy.IGNORE)
            artistDao.updateArtistSaveFav(artist.getId(),
                    artist.getName(),
                    artist.getThumbnailUrl(),
                    artist.getPhotoUrl(),
                    artist.getBiography(),
                    artist.getNumberOfAlbums(),
                    artist.getNumberOfFans());
        return OnConflictStrategy.REPLACE;
    }

    private void updateFav(int id, boolean favorite){
        artistDao.updateFav(id, favorite);
    }


}
