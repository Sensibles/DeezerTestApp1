package artur.pl.deezertestapp.Model.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by artur on 09.12.2017.
 *
 * Entity is also used as object to map JSON response from retrofit
 */
@Entity
public class Track {

    @PrimaryKey
    private int id;
    private int artistId;
    private int albumId;
    private String title;
    private String releaseDate;
    private String previewUrl;

    public Track(int id, int artistId, int albumId, String title, String releaseDate, String previewUrl) {
        this.id = id;
        this.artistId = artistId;
        this.albumId = albumId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.previewUrl = previewUrl;
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

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
