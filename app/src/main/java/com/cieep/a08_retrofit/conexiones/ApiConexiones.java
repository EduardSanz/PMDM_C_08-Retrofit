package com.cieep.a08_retrofit.conexiones;

import com.cieep.a08_retrofit.modelos.Album;
import com.cieep.a08_retrofit.modelos.Photo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiConexiones {

    @GET("/albums")
    Call<ArrayList<Album>> getAlbums();

    // /photos?albumId=2
    @GET("/photos?")
    Call<ArrayList<Photo>> getPhotosAlbum(@Query("albumId") String albumId);
}
