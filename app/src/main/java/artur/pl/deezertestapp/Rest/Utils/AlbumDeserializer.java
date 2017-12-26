package artur.pl.deezertestapp.Rest.Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Repository.AlbumRepository;
import artur.pl.deezertestapp.Rest.ResponseObject.AlbumResponseObject;

/**
 * Created by artur on 09.12.2017.
 */

public class AlbumDeserializer implements JsonDeserializer<AlbumResponseObject>
    {
        @Override
        public AlbumResponseObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
        JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            List<Album> list = new ArrayList<>();
            String next="";
            //TODO: FIND A BETTER WAY TO EXAMINE WHAT KIND OF JSONRESPONSE IT IS
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
                            //TODO: TRY TO MAP THOSE PARAMETERS USING ANNOTATIONS FROM RETROFIT IN ENTITY OBJECT
                            new Album(
                                    tempObject.get("id").getAsInt(),
                                    0,
                                    tempObject.get("title").getAsString(),
                                    "",
                                    tempObject.get("cover_small").getAsString(),
                                    tempObject.get("cover_xl").getAsString(),
                                    0,
                                    tempObject.get("fans").getAsInt(),
                                    tempObject.get("release_date").getAsString()
                            )
                    );
                }
            }else{
                list.add(
                        new Album(
                                jsonObject.get("id").getAsInt(),
                                jsonObject.get("artist").getAsJsonObject().get("id").getAsInt(),
                                jsonObject.get("title").getAsString(),
                                jsonObject.get("label").getAsString(),
                                jsonObject.get("cover_small").getAsString(),
                                jsonObject.get("cover_xl").getAsString(),
                                jsonObject.get("nb_tracks").getAsInt(),
                                jsonObject.get("fans").getAsInt(),
                                jsonObject.get("release_date").getAsString()
                        )
                );
            }



        return new AlbumResponseObject(list, next);
    }
}
