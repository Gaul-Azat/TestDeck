package com.example.azat.testdeck.vkAuth;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.azat.testdeck.R;

public class VkLogin extends AppCompatActivity {

    private final String REQUEST="https://oauth.vk.com/authorize?client_id=5421815&display=mobile&redirect_uri=https://oauth.vk.com/blank.html&scope=photos&response_type=token&v=5.50";

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vklogin);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(REQUEST);
        webView.setWebViewClient(new WebVKClient(getApplicationContext()));
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:
                if (webView!=null){
                    webView.loadUrl(REQUEST);
                    webView.setWebViewClient(new WebVKClient(getApplicationContext()));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
