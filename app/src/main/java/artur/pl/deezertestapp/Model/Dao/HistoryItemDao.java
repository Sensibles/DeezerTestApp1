package artur.pl.deezertestapp.Model.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Model.Entity.HistoryItem;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by artur on 26.01.2018.
 */

@Dao
public interface HistoryItemDao {

    @Query("SELECT * FROM HistoryItem where text like :text || '%'")
    LiveData<List<HistoryItem>> getHistoryItemsForText(String text);

    @Query("SELECT * FROM HistoryItem order by datetime")
    LiveData<List<HistoryItem>> getAllHistoryItems();

    @Insert(onConflict = REPLACE)
    Long insertHistoryItem(HistoryItem historyItem);

    @Query("DELETE FROM HistoryItem")
    void deleteAllHistoryItems();

    @Delete
    void deleteHistoryItem(HistoryItem historyItem);



}
