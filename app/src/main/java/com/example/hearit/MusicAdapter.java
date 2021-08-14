package com.example.hearit;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyVieHolder> {

    private Context mcontext;
    static ArrayList<MusicFiles> mFiles;

    MusicAdapter(Context mcontext,ArrayList<MusicFiles>mFiles){
        this.mFiles=mFiles;
        this.mcontext = mcontext;
    }
    @NonNull
    @Override
    public MyVieHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (mcontext).inflate (R.layout.music_items,parent,false);
        return new MyVieHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull MyVieHolder holder, int position) {
        holder.file_name.setText (mFiles.get (position).getTitle ());
        /*byte[] image = getAlbum (mFiles.get (position).getPath ());
        if(image != null){
            Glide.with (mcontext).asBitmap ()
                    .load (image)
                    .into (holder.album_art);
        }
        else {
            Glide.with (mcontext)
                    .load (R.drawable.c)
                    .into (holder.album_art);
        }*/
        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent (mcontext,PlayerActivity.class);
                intent.putExtra ("position",position);
                mcontext.startActivity (intent);
            }
        });
        holder.menuMore.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                PopupMenu popupMenu = new PopupMenu (mcontext,v);
                popupMenu.getMenuInflater ().inflate (R.menu.popup,popupMenu.getMenu ());
                popupMenu.show ();
                popupMenu.setOnMenuItemClickListener (item -> {
                    switch (item.getItemId ()){
                        case R.id.delete:
                            Toast.makeText (mcontext, "Delete Clicked", Toast.LENGTH_SHORT).show ();
                            deleteFile(position ,v);
                            break;
                    }
                    return true;
                });
            }
        });
    }

    private void deleteFile(int position,View v) {
        Uri contentUri = ContentUris.withAppendedId (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, Long.parseLong (mFiles.get (position).getId ()));
        File file = new File (mFiles.get (position).getPath ());
        boolean deleted = file.delete ();
        if (deleted) {
            mcontext.getContentResolver ().delete (contentUri,null,null);
            mFiles.remove (position);
            notifyItemRemoved (position);
            notifyItemRangeChanged (position, mFiles.size ());
            Snackbar.make (v, "File Deleted", Snackbar.LENGTH_LONG).show ();
        }
        else {
            Snackbar.make (v,"Can't Delete the File!",Snackbar.LENGTH_LONG).show ();
        }
    }

    @Override
    public int getItemCount () {
        return mFiles.size ();
    }


    public class MyVieHolder extends RecyclerView.ViewHolder{

        TextView file_name;
        ImageView menuMore;
        public MyVieHolder(@NonNull View itemView){
            super(itemView);
            file_name = itemView.findViewById (R.id.music_file_name);
            menuMore = itemView.findViewById (R.id.menumore);
            //album_art = itemView.findViewById (R.id.music_img);
        }
    }
    //to get defualt music image
   /* private byte [] getAlbum(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever ();
        retriever.setDataSource (uri);
        byte[]art = retriever.getEmbeddedPicture ();
        retriever.release ();
        ret urn art;
    }*/
    void updateList (ArrayList<MusicFiles>musicFilesArrayList){
        mFiles = new ArrayList<> ();
        mFiles.addAll (musicFilesArrayList);
        notifyDataSetChanged ();
    }

}
