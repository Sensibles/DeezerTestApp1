package artur.pl.deezertestapp.Model.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Track;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by artur on 09.12.2017.
 */

@Dao
public interface TrackDao {

    @Query("SELECT * FROM Track where id = :id")
    LiveData<Track> getTrackForId(int id);

    @Query("SELECT * FROM Track where albumId = :id")
    LiveData<List<Track>> getTracksForAlbumId(int id);

    @Query("SELECT * FROM Track where artistId = :id")
    LiveData<List<Track>> getTracksForArtistId(int id);

    @Query("SELECT * FROM Track")
    LiveData<List<Track>> getAllTracks();

    @Insert(onConflict = REPLACE)
    Long insertTrack(Track album);

    @Delete
    void deleteTrack(Track album);
}
