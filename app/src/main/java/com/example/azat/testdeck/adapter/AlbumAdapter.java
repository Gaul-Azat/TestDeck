package com.example.azat.testdeck.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azat.testdeck.PhotoList;
import com.example.azat.testdeck.R;
import com.example.azat.testdeck.entity.AlbumResponse;
import com.example.azat.testdeck.utils.ImageManager;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView albumName;
        ImageView firstPhoto;
        TextView countPhoto;
        public ViewHolder(View v) {
            super(v);
            cv=(CardView)v.findViewById(R.id.album_cv);
            albumName=(TextView)v.findViewById(R.id.album_name);
            firstPhoto=(ImageView)v.findViewById(R.id.first_photo);
            countPhoto=(TextView)v.findViewById(R.id.count_photo);
        }
    }

    List<AlbumResponse> albums;
    Context applicationContext;

    public AlbumAdapter(List<AlbumResponse> albums, Context applicationContext) {
        this.albums = albums;
        this.applicationContext = applicationContext;
    }

    public void setAlbums(List<AlbumResponse> albums) {
        this.albums = albums;
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.ViewHolder holder, final int position) {
        if (albums.get(position)!=null) {
            holder.albumName.setText(albums.get(position).getTitle());
            holder.countPhoto.setText("Всего фотографий: "+albums.get(position).getSize());
            if (albums.get(position).getThumb_id()!=0){
                ImageManager imageManager=new ImageManager();
                String src=albums.get(position).getThumb_src();
                imageManager.fetchImage(applicationContext,3600,src,holder.firstPhoto);
            }
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), PhotoList.class);
                intent.putExtra("album_id",albums.get(position).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
