package artur.pl.deezertestapp.Model.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by artur on 09.12.2017.
 *
 *
 */
@Entity
public class Artist {
    @PrimaryKey
    private int id;
    private String name;
    private String thumbnailUrl;
    private String photoUrl;
    private String biography;
    private int numberOfAlbums;
    private int numberOfFans;   //TODO: TRY TO DOWNLOAD ARRAY OF FANS

    public Artist(int id, String name, String thumbnailUrl, String photoUrl, String biography, int numberOfAlbums, int numberOfFans) {
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.photoUrl = photoUrl;
        this.biography = biography;
        this.numberOfAlbums = numberOfAlbums;
        this.numberOfFans = numberOfFans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public void setNumberOfAlbums(int numberOfAlbums) {
        this.numberOfAlbums = numberOfAlbums;
    }

    public int getNumberOfFans() {
        return numberOfFans;
    }

    public void setNumberOfFans(int numberOfFans) {
        this.numberOfFans = numberOfFans;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
