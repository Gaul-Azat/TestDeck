package com.example.azat.testdeck.vkAuth;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class VkSession {

    private SharedPreferences _prefs;
    private Context _context;
    private final String PREFS_NAME = "Vk:Settings";
    private SharedPreferences.Editor _editor;

    public VkSession(Context _context) {
        this._context = _context;
        _prefs=_context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        _editor=_prefs.edit();
    }

    public void saveAccessTokenFromUrl(String url){
        String atr=url.substring(32);
        atr="?"+atr;
        Uri uri=Uri.parse(atr);
        saveAccessToken(uri.getQueryParameter("access_token"),uri.getQueryParameter("expires_in"),uri.getQueryParameter("user_id"));
    }

    public void saveAccessToken(String accessToken, String expires, String userId){
        _editor.putString("VkAccessToken", accessToken);
        _editor.putString("VkExpiresIn", expires);
        _editor.putString("VkUserId", userId);
        _editor.putLong("VkAccessTime", System.currentTimeMillis());
        _editor.commit();

    }

    public String[] getAccessToken(){
        String[] params = new String[4];
        params[0] = _prefs.getString("VkAccessToken", "");
        params[1] = _prefs.getString("VkExpiresIn", "");
        params[2] = _prefs.getString("VkUserId", "");
        params[3] =  String.valueOf(_prefs.getLong("VkAccessTime",0));
        return params;
    }

    public void resetAccessToken(){
        _editor.putString("VkAccessToken", "");
        _editor.putString("VkExpiresIn", "");
        _editor.putString("VkUserId", "");
        _editor.putLong("VkAccessTime", 0);
        _editor.commit();
    }

}
