package com.example.azat.testdeck.vkAuth;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.azat.testdeck.AlbumList;

public class WebVKClient extends WebViewClient {

    public static final String CALLBACK_URL = "https://oauth.vk.com/blank.html";

    private Context _context;

    public WebVKClient(Context _context) {
        this._context = _context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("url",url);
        if (url.startsWith(CALLBACK_URL)){
            if (!url.contains("error")){
                new VkSession(_context).saveAccessTokenFromUrl(url);
                view.getContext().startActivity(new Intent(view.getContext(),AlbumList.class));
                return true;
            }
            else{
                return true;
            }
        }
        return false;
    }
}
