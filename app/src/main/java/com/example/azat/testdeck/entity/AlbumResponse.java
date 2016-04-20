package com.example.azat.testdeck.entity;


import org.json.JSONObject;
import java.util.Date;

public class AlbumResponse {

    private Long id;
    private Long thumb_id;
    private String title;
    private String description;
    private Date updated;
    private String thumb_src;
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getThumb_src() {
        return thumb_src;
    }

    public void setThumb_src(String thumb_src) {
        this.thumb_src = thumb_src;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThumb_id() {
        return thumb_id;
    }

    public void setThumb_id(Long thumb_id) {
        this.thumb_id = thumb_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public AlbumResponse() {

    }

    @Override
    public String toString() {
        return "AlbumResponse{" +
                "id=" + id +
                ", thumb_id=" + thumb_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", updated=" + updated +
                ", thumb_src=" + thumb_src +
                '}';
    }

    public static AlbumResponse albumResponseFromJson(JSONObject json){
        AlbumResponse album=null;
        try {
            album =new AlbumResponse();
            album.setId(json.getLong("aid"));
            album.setTitle(json.getString("title"));
            album.setSize(json.getInt("size"));
            if (album.getId()>0) {
                album.setDescription(json.getString("description"));
                album.setThumb_id(json.getLong("thumb_id"));
                album.setUpdated(new Date(json.getLong("updated")));
            }  else{
                album.setDescription("");
                album.setThumb_id((long)0);
                album.setUpdated(new Date(0));
            }
            if (album.getThumb_id()!=0) {
                album.setThumb_src(json.getString("thumb_src"));
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return album;
    }
}
