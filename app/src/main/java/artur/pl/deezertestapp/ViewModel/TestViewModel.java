package artur.pl.deezertestapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Model.Entity.Track;
import artur.pl.deezertestapp.Repository.AlbumRepository;
import artur.pl.deezertestapp.Repository.ArtistRepository;
import artur.pl.deezertestapp.Repository.TrackRepository;

/**
 * Created by artur on 10.12.2017.
 */

public class TestViewModel extends ViewModel{
    private ArtistRepository artistRepository;
    private AlbumRepository albumRepository;
    private TrackRepository trackRepository;

    public TestViewModel(ArtistRepository artistRepository, AlbumRepository albumRepository, TrackRepository trackRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
    }

//    public LiveData<List<Artist>> getArtistForName(String name, boolean forceOverride){
//        return artistRepository.getArtistForName(name);
//    }
//
//
//    public LiveData<Artist> getArtistForId(int id, boolean forceOverride){
//        return artistRepository.getArtistForId(id);
//    }
//
//    public LiveData<Album> getAlbumForId(int id){
//        return albumRepository.getAlbumForId(id);
//    }
//    public LiveData<List<Album>> getAlbumForArtistId(int id) {
//        albumRepository.deleteAllAlbums();
//        return albumRepository.getAlbumForArtistId(id, "0");
//    }
//
//    public LiveData<Track> getTrackForId(int id){
//        return trackRepository.getTrackForId(id);
//    }
//
//    public LiveData<List<Track>> getTrackForAlbumId(int id) {
//        return  trackRepository.getTrackForAlbumId(id, "0");
//    }
//
//    public LiveData<List<Track>> getTop5TracksForArtistId(int id){
//        return trackRepository.getTop5TrackForArtistId(id);
//    }

}
