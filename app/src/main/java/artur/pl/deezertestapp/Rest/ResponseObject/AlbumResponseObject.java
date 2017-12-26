package artur.pl.deezertestapp.Rest.ResponseObject;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Album;

/**
 * Created by artur on 11.12.2017.
 */

public class AlbumResponseObject {
    private List<Album> list;
    private String next;

    public AlbumResponseObject(List<Album> list, String next) {
        this.list = list;
        this.next = next;
    }

    public List<Album> getList() {
        return list;
    }

    public void setList(List<Album> list) {
        this.list = list;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
