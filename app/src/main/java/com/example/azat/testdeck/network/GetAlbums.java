package com.example.azat.testdeck.network;



import android.os.AsyncTask;
import android.util.Log;

import com.example.azat.testdeck.adapter.AlbumAdapter;
import com.example.azat.testdeck.entity.AlbumResponse;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GetAlbums extends AsyncTask<String,Integer,List<AlbumResponse>> {

    AlbumAdapter adapter;

    public GetAlbums(AlbumAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<AlbumResponse> doInBackground(String... params) {
        BufferedReader reader;
        List<AlbumResponse> albums=new ArrayList<>();
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
                        Log.d("Album",jsons.getJSONObject(i).toString());
                        albums.add(AlbumResponse.albumResponseFromJson(jsons.getJSONObject(i)));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        return albums;
    }
    @Override
    protected void onPostExecute(List<AlbumResponse> albumResponses) {
        adapter.setAlbums(albumResponses);
        synchronized (adapter){
            adapter.notifyItemRangeChanged(1,albumResponses.size());
        }
    }
}
