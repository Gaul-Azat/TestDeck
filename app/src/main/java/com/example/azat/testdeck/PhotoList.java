package com.example.azat.testdeck;


import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.example.azat.testdeck.adapter.PhotoAdapter;
import com.example.azat.testdeck.entity.PhotoResponse;
import com.example.azat.testdeck.network.GetPhoto;
import com.example.azat.testdeck.utils.Cookie;
import com.example.azat.testdeck.vkAuth.VkLogin;
import com.example.azat.testdeck.vkAuth.VkSession;

import java.util.ArrayList;
import java.util.List;

public class PhotoList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String request="https://api.vk.com/method/photos.get?rev=1&count=20&album_id=";
    private long albumID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        albumID=getIntent().getLongExtra("album_id",(long)0);
        request=request.concat(Long.toString(albumID));
        request=request.concat("&owner_id=");

        mRecyclerView = (RecyclerView) findViewById(R.id.photo_list);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<PhotoResponse> photoResponses=new ArrayList<>();
        mAdapter = new PhotoAdapter(photoResponses,getFragmentManager(),getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        GetPhoto getPhoto=new GetPhoto(mAdapter);
        String userId=new VkSession(getApplicationContext()).getAccessToken()[2];
        getPhoto.execute(request+userId);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Fragment fragment=getFragmentManager().findFragmentByTag("photo_fragment");
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) {
            if (fragment == null) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }
}
