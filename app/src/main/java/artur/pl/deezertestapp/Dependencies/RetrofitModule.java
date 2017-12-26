package artur.pl.deezertestapp.Dependencies;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Model.Entity.Track;
import artur.pl.deezertestapp.Rest.AlbumClient;
import artur.pl.deezertestapp.Rest.ArtistClient;
import artur.pl.deezertestapp.Rest.ResponseObject.AlbumResponseObject;
import artur.pl.deezertestapp.Rest.ResponseObject.ArtistResponseObject;
import artur.pl.deezertestapp.Rest.ResponseObject.TrackResponseObject;
import artur.pl.deezertestapp.Rest.TrackClient;
import artur.pl.deezertestapp.Rest.Utils.AlbumDeserializer;
import artur.pl.deezertestapp.Rest.Utils.ArtistDeserializer;
import artur.pl.deezertestapp.Rest.Utils.TrackDeserializer;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by artur on 10.12.2017.
 */

@Module
public class RetrofitModule {

    private String API_BASE_URL;

    public RetrofitModule(String API_BASE_URL) {
        this.API_BASE_URL = API_BASE_URL;
    }

    @Provides
    @Singleton
    OkHttpClient.Builder providesOkHttpClientBuilder(){
        return new OkHttpClient.Builder();
    }

    //TODO: REDUCE BOILERPLATE CODE  IN GSON PROVIDERS
    @Provides @Named("Artist")
    @Singleton
    Gson provideGsonWithArtistDeserializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.registerTypeAdapter(ArtistResponseObject.class, new ArtistDeserializer());
        return gsonBuilder.create();
    }

    @Provides @Named("Album")
    @Singleton
    Gson provideGsonWithAlbumDeserializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.registerTypeAdapter(AlbumResponseObject.class, new AlbumDeserializer());
        return gsonBuilder.create();
    }

    @Provides @Named("Track")
    @Singleton
    Gson provideGsonWithTrackDeserializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.registerTypeAdapter(TrackResponseObject.class, new TrackDeserializer());
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    ArtistClient providesArtistClient(OkHttpClient.Builder httpClient,@Named("Artist") Gson gson){
       return  new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
                .create(ArtistClient.class);
    }

    @Provides
    @Singleton
    AlbumClient providesAlbumClient(OkHttpClient.Builder httpClient,@Named("Album") Gson gson){
        return  new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
                .create(AlbumClient.class);
    }

    @Provides
    @Singleton
    TrackClient providesTrackClient(OkHttpClient.Builder httpClient,@Named("Track") Gson gson){
        return  new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
                .create(TrackClient.class);
    }



}
