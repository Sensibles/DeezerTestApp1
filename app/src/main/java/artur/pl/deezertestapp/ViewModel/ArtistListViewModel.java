package artur.pl.deezertestapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Repository.ArtistRepository;

/**
 * Created by artur on 01.02.2018.
 */

public class ArtistListViewModel extends ViewModel {
    private ArtistRepository artistRepository;

    public ArtistListViewModel(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public LiveData<List<Artist>> getFirstTwentyArtist(boolean forceOverride){
        return artistRepository.getArtistForIdBetween(1,20, forceOverride);
    }

    public LiveData<List<Artist>> getArtistsForName(String name, boolean forceOverride){
        return artistRepository.getArtistsForName(name, forceOverride);
    }

    public void updateArtist(Artist artist){
        artistRepository.insertOrUpdateArtist(artist);
    }

    public void updateArtistFav(int id, boolean favorite){
        artistRepository.updateFavorite(id, favorite);
    }
}
