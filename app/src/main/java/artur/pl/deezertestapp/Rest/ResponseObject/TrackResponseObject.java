package artur.pl.deezertestapp.Rest.ResponseObject;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Track;

/**
 * Created by artur on 16.12.2017.
 */

public class TrackResponseObject {
    private List<Track> list;
    private String next;

    public TrackResponseObject(List<Track> list, String next) {
        this.list = list;
        this.next = next;
    }

    public List<Track> getList() {
        return list;
    }

    public void setList(List<Track> list) {
        this.list = list;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
