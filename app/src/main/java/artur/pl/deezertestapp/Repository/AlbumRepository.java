package artur.pl.deezertestapp.Repository;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Dao.AlbumDao;
import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Repository.Utils.ProgressDialogAsyncTask;
import artur.pl.deezertestapp.Rest.AlbumClient;
import artur.pl.deezertestapp.Rest.ResponseObject.AlbumResponseObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by artur on 10.12.2017.
 */

public class AlbumRepository {
    private AlbumDao albumDao;
    private AlbumClient client;
    private Application application;

    @Inject
    public AlbumRepository(AlbumDao albumDao, AlbumClient client, Application application) {
        this.albumDao = albumDao;
        this.client = client;
        this.application = application;
    }

    public LiveData<Album> getAlbumForId(int id){
        Call<AlbumResponseObject> call = client.getAlbumForId(String.valueOf(id));
        call.enqueue(new Callback<AlbumResponseObject>() {
            @Override
            public void onResponse(Call<AlbumResponseObject> call, Response<AlbumResponseObject> response) {
                if(response.body() == null)
                    Log.e("RETROFIT", response.message());
                else {
                    Album album = response.body().getList().get(0);
                    if(album != null)
                    new AlbumRepository.AddAlbum((DeezerTestApp) application,
                            "Synchronizacja albumów").execute(album);
                }
            }
            @Override
            public void onFailure(Call<AlbumResponseObject> call, Throwable t) {
                Log.e("RETROFIT", "RETROFIT JUST FUCKED UP");
                t.printStackTrace();
            }
        });
        return albumDao.getAlbumForId(id);
    }

    public MediatorLiveData<List<Album>> getAlbumForArtistId(final int id, final boolean forceOverride){
        final MediatorLiveData<List<Album>> albumMediatorLiveData = new MediatorLiveData<>();
        final LiveData<List<Album>> dbSource = albumDao.getAlbumsForArtistId(id);
        albumMediatorLiveData.addSource(dbSource, new Observer<List<Album>>() {

            @Override
            public void onChanged(@Nullable List<Album> artists) {
                if(artists.isEmpty() || forceOverride) {
                    albumMediatorLiveData.removeSource(dbSource);
                    getAlbumForArtistIdFromWS(id, "0");
                    albumMediatorLiveData.addSource(dbSource, new Observer<List<Album>>() {
                        @Override
                        public void onChanged(@Nullable List<Album> value) {
                            albumMediatorLiveData.setValue(value);
                        }
                    });
                }
                else {
                    albumMediatorLiveData.setValue(artists);
                }
            }
        });
        return albumMediatorLiveData;
    }

    public LiveData<List<Album>> getAlbumForArtistIdFromWS(final int id, final String index){
        Call<AlbumResponseObject> call = client.getAlbumsForArtistId(String.valueOf(id), index);
        call.enqueue(new Callback<AlbumResponseObject>() {
            @Override
            public void onResponse(Call<AlbumResponseObject> call, Response<AlbumResponseObject> response) {
                if(response.body() == null) {
                    Log.e("RETROFIT", response.raw().toString());
                }
                else {
                    List<Album> Albums= response.body().getList();
                    if(Albums != null) {
                        new AddListOfAlbumsForArtistId((DeezerTestApp) application,
                                "Synchronizacja albumów",
                                id).execute(Albums);
                        if (!response.body().getNext().isEmpty())
                            getAlbumForArtistIdFromWS(id, response.body().getNext());
                    }
                }
            }
            @Override
            public void onFailure(Call<AlbumResponseObject> call, Throwable t) {
                Log.e("RETROFIT", "RETROFIT JUST FUCKED UP");
                t.printStackTrace();
            }
        });
        return albumDao.getAlbumsForArtistId(id);
    }

    public void deleteAllAlbums(){
        new DeleteAlbums((DeezerTestApp) application,
                "Synchronizacja albumów").execute();
    }

    private class AddListOfAlbumsForArtistId extends ProgressDialogAsyncTask<List<Album>, Void, Void> {
        private int artistId;

        public AddListOfAlbumsForArtistId(DeezerTestApp application, String msgText, int artistId){
            super(application, msgText);
            this.artistId = artistId;
        }

        @Override
        protected Void doInBackground(List<Album>... Albums){
            for(Album album : Albums[0]) {
                album.setArtistId(artistId);
                createNewAlbum(album);
            }
            return null;
        }
    }

    //Async tasks

    private class AddAlbum extends ProgressDialogAsyncTask<Album, Void, Void> {
        public AddAlbum(DeezerTestApp application, String msgText) {
            super(application, msgText);
        }

        @Override
        protected Void doInBackground(Album... Albums){
            createNewAlbum(Albums[0]);
            return null;
        }
    }

    private class DeleteAlbums extends ProgressDialogAsyncTask<Object, Void, Void> {
        public DeleteAlbums(DeezerTestApp application, String msgText) {
            super(application, msgText);
        }
        @Override
        protected Void doInBackground(Object... o){
            albumDao.deleteAllAlbums();
            return null;
        }
    }

    public long createNewAlbum(Album Album){
        return albumDao.insertAlbum(Album);
    }
}
