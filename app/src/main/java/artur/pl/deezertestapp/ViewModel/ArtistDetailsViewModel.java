package artur.pl.deezertestapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Repository.AlbumRepository;
import artur.pl.deezertestapp.Repository.ArtistRepository;

/**
 * Created by artur on 14.02.2018.
 * Valentines Day :D
 */

public class ArtistDetailsViewModel extends ViewModel {
    private ArtistRepository artistRepository;
    private AlbumRepository albumRepository;

    public ArtistDetailsViewModel(ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    public LiveData<Artist> getArtistForId(int id, boolean forceOverride){
        return artistRepository.getArtistForId(id, forceOverride);
    }

    public LiveData<List<Album>> getAlbumsForArtist(int id, boolean forceOverride){
        return albumRepository.getAlbumForArtistId(id, forceOverride);
    }

    public void updateArtistFav(int id, boolean favorite){
        artistRepository.updateFavorite(id, favorite);
    }
}
