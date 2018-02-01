package artur.pl.deezertestapp.Dependencies;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import artur.pl.deezertestapp.Model.Dao.AlbumDao;
import artur.pl.deezertestapp.Model.Dao.ArtistDao;
import artur.pl.deezertestapp.Model.Dao.HistoryItemDao;
import artur.pl.deezertestapp.Model.Dao.TrackDao;
import artur.pl.deezertestapp.Model.Db.AppDatabase;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Model.Entity.HistoryItem;
import artur.pl.deezertestapp.Model.Entity.Track;
import artur.pl.deezertestapp.Repository.AlbumRepository;
import artur.pl.deezertestapp.Repository.ArtistRepository;
import artur.pl.deezertestapp.Repository.HistoryItemRepository;
import artur.pl.deezertestapp.Repository.TrackRepository;
import artur.pl.deezertestapp.Rest.AlbumClient;
import artur.pl.deezertestapp.Rest.ArtistClient;
import artur.pl.deezertestapp.Rest.TrackClient;
import artur.pl.deezertestapp.ViewModel.CustomViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by artur on 10.12.2017.
 */
@Module
public class RoomModule {
    private final AppDatabase database;

    public RoomModule(Application application){
        this.database = Room.databaseBuilder(
                application,
                AppDatabase.class,
                "Databse.db"
        ).fallbackToDestructiveMigration()  //TODO: This option will drop all tables when version of db will be changed.
                .build();
    }

    @Provides
    @Singleton
    AppDatabase providesAppDatabase(Application application){
        return database;
    }


    //ALBUM
    @Provides
    @Singleton
    AlbumRepository providesAlbumRepository(AlbumDao albumDao, AlbumClient albumClient, Application application){
        return new AlbumRepository(albumDao, albumClient, application);
    }

    @Provides
    @Singleton
    AlbumDao providesAlbumDao(AppDatabase database){
        return database.albumDao();
    }
    //

    //ARTIST
    @Provides
    @Singleton
    ArtistRepository providesArtistRepository(ArtistDao artistDao, ArtistClient artistClient, Application application){
        return new ArtistRepository(artistDao, artistClient, application);
    }

    @Provides
    @Singleton
    ArtistDao providesArtistDao(AppDatabase database){
        return database.artistDao();
    }
    //

    //TRACK
    @Provides
    @Singleton
    TrackRepository providesTrackRepository(TrackDao trackDao, TrackClient trackClient, Application application){
        return new TrackRepository(trackDao, trackClient, application);
    }

    @Provides
    @Singleton
    TrackDao providesTrackDao(AppDatabase database){
        return database.trackDao();
    }
    //

    //HISTORY ITEM
    @Provides
    @Singleton
    HistoryItemRepository providesHistoryItemRepository(HistoryItemDao historyItemDao, Application application){
        return new HistoryItemRepository(application, historyItemDao);
    }

    @Provides
    @Singleton
    HistoryItemDao providesHistoryItemDao(AppDatabase database){
        return database.historyItemDao();
    }
    //

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(ArtistRepository artistRepository, AlbumRepository albumRepository, TrackRepository trackRepository, HistoryItemRepository historyItemRepository){
        return new CustomViewModelFactory(artistRepository, albumRepository, trackRepository, historyItemRepository);
    }

}
