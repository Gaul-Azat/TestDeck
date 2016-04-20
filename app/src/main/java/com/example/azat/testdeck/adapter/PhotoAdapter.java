package com.example.azat.testdeck.adapter;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.azat.testdeck.PhotoDialog;
import com.example.azat.testdeck.R;
import com.example.azat.testdeck.entity.PhotoResponse;
import com.example.azat.testdeck.utils.ImageManager;

import java.util.List;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView firstPhoto;
        public ViewHolder(View v) {
            super(v);
            cv=(CardView)v.findViewById(R.id.photo_cv);
            firstPhoto=(ImageView)v.findViewById(R.id.photo_frame);
        }
    }

    List<PhotoResponse> photos;
    FragmentManager manager;
    Context applicationContext;

    public PhotoAdapter(List<PhotoResponse> photos, FragmentManager manager, Context applicationContext) {
        this.photos = photos;
        this.manager = manager;
        this.applicationContext = applicationContext;
    }

    public void setPhotos(List<PhotoResponse> photos) {
        this.photos = photos;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_card,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (photos.get(position)!=null) {
            ImageManager imageManager=new ImageManager();
            imageManager.fetchImage(applicationContext,3600,photos.get(position).getPhotoMiniUrl(),holder.firstPhoto);
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new PhotoDialog();
                Bundle args=new Bundle();
                args.putString("photo",photos.get(position).getPhotoMaxUrl());
                dialogFragment.setArguments(args);
                dialogFragment.show(manager,"photo_fragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
