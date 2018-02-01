package artur.pl.deezertestapp.Model.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.IdRes;

import java.util.Date;

import artur.pl.deezertestapp.Model.Utils.TimestampConverter;

/**
 * Created by artur on 26.01.2018.
 */

@Entity
public class HistoryItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    @TypeConverters({TimestampConverter.class})
    private Date datetime;

    public HistoryItem(String text, Date datetime) {
        this.text = text;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
