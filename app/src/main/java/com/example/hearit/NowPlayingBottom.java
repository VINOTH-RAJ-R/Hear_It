package com.example.hearit;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.hearit.MainActivity.ARTIST_TO_FRAG;
import static com.example.hearit.MainActivity.PATH_TO_FRAG;
import static com.example.hearit.MainActivity.SHOW_MINI_PLAYER;
import static com.example.hearit.MainActivity.SONG_NAME_TO_FRAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingBottom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingBottom extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView nextBtn, albumArt, prevBtn;
    TextView artist, songName;
    FloatingActionButton playPauseBtn;
    View view;


    public NowPlayingBottom () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingBottom.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingBottom newInstance (String param1, String param2) {
        NowPlayingBottom fragment = new NowPlayingBottom ();
        Bundle args = new Bundle ();
        args.putString (ARG_PARAM1, param1);
        args.putString (ARG_PARAM2, param2);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if (getArguments () != null) {
            mParam1 = getArguments ().getString (ARG_PARAM1);
            mParam2 = getArguments ().getString (ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate (R.layout.fragment_now_playing_bottom, container, false);
        artist = view.findViewById (R.id.song_artist_miniPlayer);
        songName = view.findViewById (R.id.song_name_miniPlayer);
        albumArt = view.findViewById (R.id.bottom_album_art);
        nextBtn = view.findViewById (R.id.skip_next_bottom);
        prevBtn = view.findViewById (R.id.skip_prev_bottom);
        playPauseBtn = view.findViewById (R.id.play_pause_miniPlayer);

        nextBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Toast.makeText (getContext (),"next",Toast.LENGTH_SHORT).show ();
            }
        });
        prevBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                
            }
        });
        playPauseBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

            }
        });

        return view;
    }

    @Override
    public void onResume () {

        super.onResume ();
        if (SHOW_MINI_PLAYER) {
            if (PATH_TO_FRAG != null) {
                byte[] art = getAlbum (PATH_TO_FRAG);
                if (art!=null){
                    Glide.with (getContext ()).load (art)
                            .into (albumArt);
                }else {
                    Glide.with (getContext ()).load (R.drawable.logo)
                            .into (albumArt);
                }
                songName.setText (SONG_NAME_TO_FRAG);
                artist.setText (ARTIST_TO_FRAG);
            }
        }
       /*
        //getting path of the song
        if (SHOW_MINI_PLAYER) {
            if (PATH_TO_FRAG != null) {
                byte[] art = getAlbum (PATH_TO_FRAG);
                Glide.with (getContext ()).load (art)
                        .into (albumArt);
                songName.setText (PATH_TO_FRAG);
            }
        }*/
    }

    private byte [] getAlbum(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever ();
        retriever.setDataSource (uri);
        byte[]art = retriever.getEmbeddedPicture ();
        retriever.release ();
        return art;
    }


}