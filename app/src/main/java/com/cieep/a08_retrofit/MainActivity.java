package com.cieep.a08_retrofit;

import android.content.DialogInterface;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;

import com.cieep.a08_retrofit.adapters.AlbumsAdapter;
import com.cieep.a08_retrofit.conexiones.ApiConexiones;
import com.cieep.a08_retrofit.conexiones.RetrofitObject;
import com.cieep.a08_retrofit.modelos.Album;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;



import com.cieep.a08_retrofit.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    private List<Album> albums;
    private AlbumsAdapter adapter;
    private RecyclerView.LayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        albums = new ArrayList<>();
        adapter = new AlbumsAdapter(albums, R.layout.album_view_holder, this);
        lm = new LinearLayoutManager(this);

        binding.contentMain.contenedorAlbums.setAdapter(adapter);
        binding.contentMain.contenedorAlbums.setLayoutManager(lm);

        doGetAlbums();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarAlbum().show();
            }
        });
    }

    private AlertDialog insertarAlbum() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear album");
        EditText txtTitulo = new EditText(this);
        builder.setView(txtTitulo);
        builder.setCancelable(false);
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("INSERTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtTitulo.getText().toString().isEmpty()) {
                    Album a = new Album();
                    a.setUserId(1);
                    a.setTitulo(txtTitulo.getText().toString());
                    doPostAlbum(a);
                    // doPostAlbumForm(1, txtTitulo.getText().toString());
                }
            }
        });
        return builder.create();
    }

    private void doPostAlbumForm(int i, String toString) {
        Retrofit retrofit = RetrofitObject.getConexion();
        ApiConexiones api = retrofit.create(ApiConexiones.class);
        Call<Album> postAlbum = api.postAlbumForm(i, toString);

        postAlbum.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.code() == HttpsURLConnection.HTTP_CREATED) {
                    albums.add(0, response.body());
                    adapter.notifyItemInserted(0);
                }else {
                    Toast.makeText(MainActivity.this, "NO INSERTADO", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

            }
        });
    }

    private void doPostAlbum(Album album) {
        Retrofit retrofit = RetrofitObject.getConexion();
        ApiConexiones api = retrofit.create(ApiConexiones.class);
        Call<Album> postAlbum = api.postAlbum(album);

        postAlbum.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.code() == HttpsURLConnection.HTTP_CREATED) {
                    albums.add(0, response.body());
                    adapter.notifyItemInserted(0);
                }else {
                    Toast.makeText(MainActivity.this, "NO INSERTADO", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

            }
        });
    }

    private void doGetAlbums() {
        Retrofit retrofit = RetrofitObject.getConexion();
        ApiConexiones api = retrofit.create(ApiConexiones.class);

        Call<ArrayList<Album>> getAlbums =  api.getAlbums();

        getAlbums.enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK){
                    ArrayList<Album> temp = response.body();
                    albums.addAll(temp);
                    adapter.notifyItemRangeInserted(0, temp.size());
                }
                else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "NO TIENES INTERNETE", Toast.LENGTH_SHORT).show();
                Log.e("FAILURE", t.getLocalizedMessage());
            }
        });
    }


}