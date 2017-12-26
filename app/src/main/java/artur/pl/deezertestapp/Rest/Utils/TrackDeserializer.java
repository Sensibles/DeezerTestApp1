package artur.pl.deezertestapp.Rest.Utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.Model.Entity.Track;
import artur.pl.deezertestapp.Rest.ResponseObject.AlbumResponseObject;
import artur.pl.deezertestapp.Rest.ResponseObject.TrackResponseObject;

/**
 * Created by artur on 09.12.2017.
 */

public class TrackDeserializer implements JsonDeserializer<TrackResponseObject>
{
@Override
public TrackResponseObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
        JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        List<Track> list = new ArrayList<>();
        String next="";
        /*
        if node 'data' appears in json response, it means that response is from searching request
         */
        if(jsonObject.get("data") != null && jsonObject.get("data").isJsonArray()) {
                if(jsonObject.get("next") != null) {
                        next = jsonObject.get("next").getAsString();
                        next = next.split("=")[1];
                }
                for(JsonElement element : jsonObject.get("data").getAsJsonArray()) {
                        JsonObject tempObject = element.getAsJsonObject();
                        list.add(
                                new Track(
                                        tempObject.get("id").getAsInt(),
                                        tempObject.get("artist").getAsJsonObject().get("id").getAsInt(),
                                        tempObject.get("album")==null?0:tempObject.get("album").getAsJsonObject().get("id").getAsInt(), // if response came from tracksForAlbumId, it won't contain album node.
                                        tempObject.get("title").getAsString(),
                                        "00-00-0000", //both trackForAlbumId and top5TracksForArtistId haven't release_date node.
                                        tempObject.get("preview").getAsString()
                                )
                        );
                }
        }else{
                list.add(
                        new Track(
                                jsonObject.get("id").getAsInt(),
                                jsonObject.get("artist").getAsJsonObject().get("id").getAsInt(),
                                jsonObject.get("album").getAsJsonObject().get("id").getAsInt(),
                                jsonObject.get("title").getAsString(),
                                jsonObject.get("release_date").getAsString(),
                                jsonObject.get("preview").getAsString()
                        )
                );
        }


        return new TrackResponseObject(list,next);
        }
}
