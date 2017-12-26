package artur.pl.deezertestapp.Model.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.RequiresApi;

/**
 * Created by artur on 09.12.2017.
 *
 * Entity is also used as object to map JSON response from retrofit
 */

@Entity
public class Album {
    @PrimaryKey
    private int id;
    private int artistId;
    private String title;
    private String label;
    private String thumbnailUrl;
    private String coverUrl;
    private int numberOfTracks;
    private int numberOfFans;
    private String releaseDate;

    public Album(int id, int artistId, String title, String label, String thumbnailUrl, String coverUrl, int numberOfTracks, int numberOfFans, String releaseDate) {
        this.id = id;
        this.artistId = artistId;
        this.title = title;
        this.label = label;
        this.thumbnailUrl = thumbnailUrl;
        this.coverUrl = coverUrl;
        this.numberOfTracks = numberOfTracks;
        this.numberOfFans = numberOfFans;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public int getNumberOfFans() {
        return numberOfFans;
    }

    public void setNumberOfFans(int numberOfFans) {
        this.numberOfFans = numberOfFans;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
