package com.example.hearit;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.hearit.MainActivity.musicFiles;

public class AlbumDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView albumPhoto;
    String albumName;
    ArrayList<MusicFiles>albumSongs = new ArrayList<> ();
    AlbumDetailsAdapter albumDetailsAdapter;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_album_details);

        recyclerView = findViewById (R.id.recyclerView);
        albumPhoto = findViewById (R.id.albumphoto);
        albumName = getIntent ().getStringExtra ("albumName");
        int j = 0;
        for (int i = 0 ; i < musicFiles.size ();i++){
            if (albumName.equals (musicFiles.get(i).getAlbum ())){
                albumSongs.add (j,musicFiles.get (i));
                j++;
            }
        }
        byte[] image = getAlbum (albumSongs.get (0).getPath ());
        if (image!=null){
            Glide.with (this)
                    .load (image)
                    .into (albumPhoto);
        }else {
            Glide.with (this)
                    .load (R.drawable.logo)
                    .into (albumPhoto);
        }
    }

    @Override
    protected void onResume () {
        super.onResume ();
        if (!(albumSongs.size ()<1))
        {
            albumDetailsAdapter = new AlbumDetailsAdapter (this,albumSongs);
            recyclerView.setAdapter (albumDetailsAdapter);
            recyclerView.setLayoutManager (new LinearLayoutManager (this,
                    RecyclerView.VERTICAL,false));
        }
    }

  private byte [] getAlbum(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever ();
        retriever.setDataSource (uri);
        byte[]art = retriever.getEmbeddedPicture ();
        retriever.release ();
        return art;
    }
}