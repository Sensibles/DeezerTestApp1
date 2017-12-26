package artur.pl.deezertestapp.Model.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Album;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by artur on 09.12.2017.
 */

@Dao
public interface AlbumDao {

    @Query("SELECT * FROM Album where id = :id")
    LiveData<Album> getAlbumForId(int id);

    @Query("SELECT * FROM Album where artistId = :id")
    LiveData<List<Album>> getAlbumsForArtistId(int id);

    @Query("SELECT * FROM Album")
    LiveData<List<Album>> getAllAlbums();

    @Insert(onConflict = REPLACE)
    Long insertAlbum(Album album);

    @Delete
    void deleteAlbum(Album album);

    @Query("DELETE FROM Album")
    void deleteAllAlbums();
}
