package artur.pl.deezertestapp.Model.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Artist;

import static android.arch.persistence.room.OnConflictStrategy.FAIL;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by artur on 09.12.2017.
 */
@Dao
public interface ArtistDao {

    @Query("SELECT * FROM Artist where id = :id")
    LiveData<Artist> getArtistForId(int id);

    @Query("SELECT * FROM Artist where id BETWEEN :id_start AND :id_stop")
    LiveData<List<Artist>> getArtistForIdBetween(int id_start, int id_stop);

    @Query("SELECT * FROM Artist where name COLLATE UTF8_GENERAL_CI like '%' || :name || '%'")
    LiveData<List<Artist>> getArtistForName(String name);

    @Query("SELECT * FROM Artist")
    LiveData<List<Artist>> getAllArtists();

    @Query("SELECT * FROM Artist WHERE favorite=1")
    LiveData<List<Artist>> getFavoriteArtists();

    @Insert(onConflict = IGNORE)
    Long insertArtist(Artist artist);

    @Query("UPDATE Artist SET id =:id, name =:name, thumbnailUrl =:thumbnailUrl, photoUrl =:photoUrl, biography =:biography, numberOfAlbums =:numberOfAlbums, numberOfFans =:numberOfFans")
    void updateArtistSaveFav(int id, String name, String thumbnailUrl, String photoUrl, String biography, int numberOfAlbums, int numberOfFans);

    @Query("UPDATE Artist SET favorite =:favorite where id=:id")
    void updateFav(int id, boolean favorite);

    @Delete
    void deleteArtist(Artist artist);
}
