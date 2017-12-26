package artur.pl.deezertestapp.Model.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Artist;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by artur on 09.12.2017.
 */
@Dao
public interface ArtistDao {

    @Query("SELECT * FROM Artist where id = :id")
    LiveData<Artist> getArtistForId(int id);


    @Query("SELECT * FROM Artist where name like '%' || :name || '%'")
    LiveData<List<Artist>> getArtistForName(String name);

    @Query("SELECT * FROM Artist")
    LiveData<List<Artist>> getAllArtists();

    @Insert(onConflict = REPLACE)
    Long insertArtist(Artist artist);

    @Delete
    void deleteArtist(Artist artist);
}
