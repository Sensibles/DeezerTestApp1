package artur.pl.deezertestapp.Repository;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

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

    public LiveData<Artist> getArtistForId(int id){
        Call<ArtistResponseObject> call = client.getArtistForId(String.valueOf(id));
        call.enqueue(new Callback<ArtistResponseObject>() {
            @Override
            public void onResponse(Call<ArtistResponseObject> call, Response<ArtistResponseObject> response) {
                if(response.body() == null)
                    Log.e("RETROFIT", response.message());
                else {
                    Artist artist = response.body().getList().get(0);
                    if(artist != null)
                        new AddArtist((DeezerTestApp)application,
                                "Synchronizacja artystów").execute(artist);
                }
            }

            @Override
            public void onFailure(Call<ArtistResponseObject> call, Throwable t) {
                Log.e("RETROFIT", "RETROFIT JUST FUCKED UP");
                t.printStackTrace();
            }
        });
        return artistDao.getArtistForId(id);
    }

    public LiveData<List<Artist>> getArtistForName(String name){

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
        return artistDao.getArtistForName(name);
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

    public long createNewArtist(Artist artist){
        return artistDao.insertArtist(artist);
    }
}
