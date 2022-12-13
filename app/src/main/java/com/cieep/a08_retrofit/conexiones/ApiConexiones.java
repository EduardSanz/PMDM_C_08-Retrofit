package com.cieep.a08_retrofit.conexiones;

import com.cieep.a08_retrofit.modelos.Album;
import com.cieep.a08_retrofit.modelos.Photo;

import java.util.ArrayList;

import kotlin.jvm.JvmMultifileClass;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiConexiones {

    /**
     * GETS -> all - filtar por parametro / filtrar por PATH
     * @return
     */

    @GET("/albums")
    Call<ArrayList<Album>> getAlbums();

    // /photos?albumId=2
    @GET("/photos?")
    Call<ArrayList<Photo>> getPhotosAlbum(@Query("albumId") String albumId);

    // /albums/{2}/photos
    @GET("/albums/{albumId}/photos")
    Call<ArrayList<Photo>> getPhotosAlbumPath(@Path("albumId") String id);

    /**
     * POST -> la petición tiene que ser tipo POST
     */
    @POST("/albums")
    Call<Album> postAlbum(@Body Album a);

    @FormUrlEncoded
    @POST("/albums")
    Call<Album> postAlbumForm(@Field("userId") int idUser, @Field("title") String titulo );

    /**
     * DELETE
     */
    @DELETE("/albums/{id}")
    Call<Album> deleteAlbum(@Path("id") String idAlbum);
}
