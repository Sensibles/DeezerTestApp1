package artur.pl.deezertestapp.Repository;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Dao.TrackDao;
import artur.pl.deezertestapp.Model.Entity.Track;
import artur.pl.deezertestapp.Repository.Utils.ProgressDialogAsyncTask;
import artur.pl.deezertestapp.Rest.ResponseObject.TrackResponseObject;
import artur.pl.deezertestapp.Rest.TrackClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by artur on 10.12.2017.
 */

public class TrackRepository {

    private TrackDao trackDao;
    private TrackClient client;
    private Application application;

    @Inject
    public TrackRepository(TrackDao trackDao, TrackClient client, Application application) {
        this.trackDao = trackDao;
        this.client = client;
        this.application = application;
    }

    public LiveData<Track> getTrackForId(int id){
        Call<TrackResponseObject> call = client.getTrackForId(String.valueOf(id));
        call.enqueue(new Callback<TrackResponseObject>() {
            @Override
            public void onResponse(Call<TrackResponseObject> call, Response<TrackResponseObject> response) {

                if(response.body() == null)
                    Log.e("RETROFIT", response.message());
                else {
                    Track Track = response.body().getList().get(0);
                    new TrackRepository.AddTrack((DeezerTestApp) application,
                            "Synchronizacja utworów").execute(Track);
                }
            }

            @Override
            public void onFailure(Call<TrackResponseObject> call, Throwable t) {
                Log.e("RETROFIT", "RETROFIT JUST FUCKED UP");
                t.printStackTrace();
            }
        });
        return trackDao.getTrackForId(id);
    }


    public LiveData<List<Track>> getTop5TrackForArtistId(final int id){

        Call<TrackResponseObject> call = client.getTop5TrackForArtistId(String.valueOf(id));
        call.enqueue(new Callback<TrackResponseObject>() {
            @Override
            public void onResponse(Call<TrackResponseObject> call, Response<TrackResponseObject> response) {
                if(response.body() == null) {
                    Log.e("RETROFIT", response.raw().toString());
                }
                else {
                    List<Track> Tracks= response.body().getList();
                    if(Tracks != null) {
                        new TrackRepository.AddListOfTracksForArtistId((DeezerTestApp) application,
                                "Synchronizacja utworów").execute(Tracks);
                    }
                }
            }
            @Override
            public void onFailure(Call<TrackResponseObject> call, Throwable t) {
                Log.e("RETROFIT", "RETROFIT JUST FUCKED UP");
                t.printStackTrace();
            }
        });
        return trackDao.getTracksForArtistId(id);
    }

    public LiveData<List<Track>> getTrackForAlbumId(final int id, final String index){

        Call<TrackResponseObject> call = client.getTracksForAlbumId(String.valueOf(id), index);
        call.enqueue(new Callback<TrackResponseObject>() {
            @Override
            public void onResponse(Call<TrackResponseObject> call, Response<TrackResponseObject> response) {
                if(response.body() == null) {
                    Log.e("RETROFIT", response.raw().toString());
                }
                else {
                    List<Track> Tracks= response.body().getList();
                    if(Tracks != null) {
                        new TrackRepository.AddListOfTracksForAlbumId((DeezerTestApp) application,
                                "Synchronizacja utworów",
                                id).execute(Tracks);
                        if (!response.body().getNext().isEmpty())
                            getTrackForAlbumId(id, response.body().getNext());
                    }
                }
            }

            @Override
            public void onFailure(Call<TrackResponseObject> call, Throwable t) {
                Log.e("RETROFIT", "RETROFIT JUST FUCKED UP");
                t.printStackTrace();
            }
        });
        return trackDao.getTracksForAlbumId(id);
    }

    private class AddTrack extends ProgressDialogAsyncTask<Track, Void, Void> {
        public AddTrack(DeezerTestApp application, String msgText) {
            super(application, msgText);
        }

        @Override
        protected Void doInBackground(Track... Tracks){
            createNewTrack(Tracks[0]);
            return null;
        }
    }

    private class AddListOfTracksForAlbumId extends ProgressDialogAsyncTask<List<Track>, Void, Void> {
        private int albumId;

        public AddListOfTracksForAlbumId(DeezerTestApp application, String msgText, int albumId) {
            super(application, msgText);
            this.albumId = albumId;
        }

        @Override
        protected Void doInBackground(List<Track>... Tracks){
            for(Track Track : Tracks[0]) {
                Track.setAlbumId(albumId);
                createNewTrack(Track);
            }
            return null;
        }
    }

    private class AddListOfTracksForArtistId extends ProgressDialogAsyncTask<List<Track>, Void, Void> {
        public AddListOfTracksForArtistId(DeezerTestApp application, String msgText) {
            super(application, msgText);
        }
        @Override
        protected Void doInBackground(List<Track>... Tracks){
            for(Track Track : Tracks[0]) {
                createNewTrack(Track);
            }
            return null;
        }
    }

    private void createNewTrack(Track track){
        trackDao.insertTrack(track);
    }
}
