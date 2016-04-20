package com.example.azat.testdeck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.azat.testdeck.adapter.AlbumAdapter;
import com.example.azat.testdeck.entity.AlbumResponse;
import com.example.azat.testdeck.network.GetAlbums;
import com.example.azat.testdeck.utils.Cookie;
import com.example.azat.testdeck.vkAuth.VkLogin;
import com.example.azat.testdeck.vkAuth.VkSession;

import java.util.ArrayList;
import java.util.List;

public class AlbumList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AlbumAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final String REQUEST="https://api.vk.com/method/photos.getAlbums?need_covers=1&need_system=1&owner_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.album_list);

        mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<AlbumResponse> albums=new ArrayList<>();
        mAdapter = new AlbumAdapter(albums,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        GetAlbums getAlbums=new GetAlbums(mAdapter);
        String userId=new VkSession(getApplicationContext()).getAccessToken()[2];
        getAlbums.execute(REQUEST+userId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:
                Cookie.ClearCookies(getApplicationContext());
                startActivity(new Intent(this,VkLogin.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
