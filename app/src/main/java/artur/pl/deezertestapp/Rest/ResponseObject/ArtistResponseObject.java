package artur.pl.deezertestapp.Rest.ResponseObject;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Artist;

/**
 * Created by artur on 11.12.2017.
 */

public class ArtistResponseObject {
    private List<Artist> list;

    public ArtistResponseObject(List<Artist> list) {
        this.list = list;
    }

    public List<Artist> getList() {
        return list;
    }

    public void setList(List<Artist> list) {
        this.list = list;
    }
}
