package artur.pl.deezertestapp.Rest.Utils;

import android.util.Log;

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
import artur.pl.deezertestapp.Rest.ResponseObject.ArtistResponseObject;

import static artur.pl.deezertestapp.Constants.DEBUG_TAG;

/**
 * Created by artur on 09.12.2017.
 */

public class ArtistDeserializer implements JsonDeserializer<ArtistResponseObject>
{
    @Override
    public ArtistResponseObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        List<Artist> list = new ArrayList<>();
        Log.d(DEBUG_TAG, jsonObject.toString() );
        //TODO: FIND A BETTER WAY TO EXAMINE WHAT KIND OF JSONRESPONSE IT IS
        /*
        if node 'data' appears in json response, it means that response is from searching request
         */
        if(jsonObject.get("data") != null && jsonObject.get("data").isJsonArray()) {
            for(JsonElement element : jsonObject.get("data").getAsJsonArray()) {
               JsonObject tempObject = element.getAsJsonObject();
               list.add(
                      new Artist(
                              tempObject.get("id").getAsInt(),
                              tempObject.get("name").getAsString(),
                              tempObject.get("picture_medium").getAsString(),
                              tempObject.get("picture_big").getAsString(),
                              "",
                              tempObject.get("nb_album").getAsInt(),
                              tempObject.get("nb_fan").getAsInt()

                      )
               );

            }
        }else{
            list.add(
                    new Artist(
                            jsonObject.get("id").getAsInt(),
                            jsonObject.get("name").getAsString(),
                            jsonObject.get("picture_medium").getAsString(),
                            jsonObject.get("picture_big").getAsString(),
                            "",
                            jsonObject.get("nb_album").getAsInt(),
                            jsonObject.get("nb_fan").getAsInt()
                    )
            );
        }
        return new ArtistResponseObject(list);
    }
}
