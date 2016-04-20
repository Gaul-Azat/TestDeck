package com.example.azat.testdeck.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.azat.testdeck.adapter.AlbumAdapter;
import com.example.azat.testdeck.adapter.PhotoAdapter;
import com.example.azat.testdeck.entity.AlbumResponse;
import com.example.azat.testdeck.entity.PhotoResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetPhoto extends AsyncTask<String,Integer,List<PhotoResponse>> {

    PhotoAdapter adapter;

    public GetPhoto(PhotoAdapter adapter) {
        this.adapter = adapter;
    }


    @Override
    protected List<PhotoResponse> doInBackground(String... params) {
        BufferedReader reader;
        List<PhotoResponse> photoResponses=new ArrayList<>();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = reader.readLine();
            if (line != null) {
                line = line.substring(12);
                JSONArray jsons=new JSONArray(line);
                for (int i=0;i<jsons.length();i++)
                {
                    photoResponses.add(PhotoResponse.photoResponseFromJson(jsons.getJSONObject(i)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return photoResponses;
    }

    @Override
    protected void onPostExecute(List<PhotoResponse> photoResponses) {
        adapter.setPhotos(photoResponses);
        synchronized (adapter){
            adapter.notifyItemRangeChanged(1,photoResponses.size());
        }
    }
}
