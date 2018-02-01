package artur.pl.deezertestapp.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import artur.pl.deezertestapp.Repository.AlbumRepository;
import artur.pl.deezertestapp.Repository.ArtistRepository;
import artur.pl.deezertestapp.Repository.HistoryItemRepository;
import artur.pl.deezertestapp.Repository.TrackRepository;

/**
 * Created by artur on 10.12.2017.
 */

@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final HistoryItemRepository historyItemRepository;

    @Inject
    public CustomViewModelFactory(ArtistRepository artistRepository, AlbumRepository albumRepository, TrackRepository trackRepository, HistoryItemRepository historyItemRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
        this.historyItemRepository = historyItemRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TestViewModel.class))
            return (T) new TestViewModel(artistRepository, albumRepository, trackRepository);

        else if (modelClass.isAssignableFrom(SearchViewModel.class))
            return (T) new SearchViewModel(historyItemRepository);
//
//        else if (modelClass.isAssignableFrom(GitHubRepoListViewModel.class))
//            return (T) new GitHubRepoListViewModel(gitHubRepoRepository);
//
//        else if (modelClass.isAssignableFrom(AlbumViewModel.class))
//            return (T) new AlbumViewModel(albumRepository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }

}