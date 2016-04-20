package com.example.azat.testdeck.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class PhotoResponse{

    private long id;
    private String photoMiniUrl;
    private String photoMaxUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhotoMiniUrl() {
        return photoMiniUrl;
    }

    public void setPhotoMiniUrl(String photoMiniUrl) {
        this.photoMiniUrl = photoMiniUrl;
    }

    public String getPhotoMaxUrl() {
        return photoMaxUrl;
    }

    public void setPhotoMaxUrl(String photoMaxUrl) {
        this.photoMaxUrl = photoMaxUrl;
    }

    public PhotoResponse() {

    }

    @Override
    public String toString() {
        return "PhotoResponse{" +
                "id=" + id +
                ", photoMiniUrl=" + photoMiniUrl +
                ", photoMaxUrl=" + photoMaxUrl +
                '}';
    }

    public static PhotoResponse photoResponseFromJson(JSONObject json){
        PhotoResponse photoResponse=null;
        try {
            photoResponse=new PhotoResponse();
            photoResponse.setId(json.getLong("pid"));

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        boolean bigSize=true;
        try {
            photoResponse.setPhotoMaxUrl(json.getString("src_xxxbig"));
        }catch (JSONException e){
            try {
                photoResponse.setPhotoMaxUrl(json.getString("src_xxbig"));
            }catch (JSONException e1){
                try {
                    photoResponse.setPhotoMaxUrl(json.getString("src_xbig"));
                }catch (JSONException e2){
                    try {
                        photoResponse.setPhotoMaxUrl(json.getString("src_big"));
                    } catch (JSONException e3) {
                        try {
                            photoResponse.setPhotoMaxUrl(json.getString("src"));
                            photoResponse.setPhotoMiniUrl(json.getString("src"));
                            bigSize=false;
                        } catch (JSONException e4) {
                            e4.printStackTrace();
                        }
                    }
                }
            }
        }
        if (bigSize){
            try {
                photoResponse.setPhotoMiniUrl(json.getString("src_big"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return photoResponse;
    }
}
