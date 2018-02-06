package artur.pl.deezertestapp.Model.Db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import artur.pl.deezertestapp.Model.Dao.AlbumDao;
import artur.pl.deezertestapp.Model.Dao.ArtistDao;
import artur.pl.deezertestapp.Model.Dao.HistoryItemDao;
import artur.pl.deezertestapp.Model.Dao.TrackDao;
import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Model.Entity.HistoryItem;
import artur.pl.deezertestapp.Model.Entity.Track;

/**
 * Created by artur on 09.12.2017.
 */
@android.arch.persistence.room.Database(entities = {Artist.class, Album.class, Track.class, HistoryItem.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArtistDao artistDao();
    public abstract AlbumDao albumDao();
    public abstract TrackDao trackDao();
    public abstract HistoryItemDao historyItemDao();
}
