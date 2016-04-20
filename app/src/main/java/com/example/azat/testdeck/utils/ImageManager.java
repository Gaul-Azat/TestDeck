package com.example.azat.testdeck.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Vector;

public class ImageManager {
    public String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void fileSave(InputStream is, FileOutputStream outputStream) {
        int i;
        try {
            while ((i = is.read()) != -1) {
                outputStream.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Vector<ImageView> downloaded = new Vector<ImageView>();


    public boolean findObject(ImageView object) {
        for (int i = 0; i < downloaded.size(); i++) {
            if (downloaded.elementAt(i).equals(object)) {
                return true;
            }
        }
        return false;
    }

    public void fetcnImageWebView(final Context context, final int cacheTime, final String iUrl, final WebView iView, final Context activityContext){
        if (iView!=null){
            final File file = new File(context.getExternalCacheDir(), md5(iUrl)
                    + ".cache");
            if (file.exists()) {
                String data="<html>\n" +
                        "<body bgcolor=\"transparent\">\n" +
                        "    <table width=\"100%\" height=\"100%\">\n" +
                        "        <tr>\n" +
                        "            <td align=\"center\" valign=\"center\">\n" +
                        "                <img src=\""+file.getName()+"\">\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </table>\n" +
                        "</body>";
                Log.d("s",data);
                iView.loadDataWithBaseURL("file://"+file.getPath(),data,"text/html","utf-8","");
                return;
            }
            new AsyncTask<String, Void, String>() {
                public ProgressDialog dialog;
                protected void onPreExecute() {
                    dialog = new ProgressDialog(activityContext);
                    dialog.setMessage("Загрузка...");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(true);
                    dialog.show();
                }
                protected String doInBackground(String... iUrl) {
                    downloadImage(context, cacheTime, iUrl[0], null);
                    return iUrl[0];
                }
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    dialog.dismiss();
                    if (iView != null) {
                        String data="<html>\n" +
                                "<body bgcolor=\"transparent\">\n" +
                                "    <table width=\"100%\" height=\"100%\">\n" +
                                "        <tr>\n" +
                                "            <td align=\"center\" valign=\"center\">\n" +
                                "                <img src=\""+file.getName()+"\">\n" +
                                "            </td>\n" +
                                "        </tr>\n" +
                                "    </table>\n" +
                                "</body>";
                        Log.d("s",data);
                        iView.loadDataWithBaseURL("file://"+file.getPath(),data,"text/html","utf-8","");
                    }
                }
            }.execute(new String[] { iUrl });
        }

    }

    private Bitmap downloadImage(Context context, int cacheTime, String iUrl, ImageView iView) {
        Bitmap bitmap = null;
        if (cacheTime != 0) {
            File file = new File(context.getExternalCacheDir(), md5(iUrl)
                    + ".cache");
            long time = new Date().getTime() / 1000;
            long timeLastModifed = file.lastModified() / 1000;
            try {
                if (file.exists()) {
                    if (timeLastModifed + cacheTime < time) {
                        file.delete();
                        file.createNewFile();
                        fileSave(new URL(iUrl).openStream(),
                                new FileOutputStream(file));
                    }
                } else {
                    file.createNewFile();
                    fileSave(new URL(iUrl).openStream(), new FileOutputStream(
                            file));
                }
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmap == null) {
                file.delete();
            }
        } else {
            try {
                bitmap = BitmapFactory.decodeStream(new URL(iUrl).openStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (iView != null) {
            downloaded.remove(iView);
        }
        return bitmap;
    }
    public void fetchImage(final Context context, final int cacheTime, final String url, final ImageView iView) {
        if (iView != null) {
            if (findObject(iView)) {
                return;
            }
            downloaded.add(iView);
        }
        new AsyncTask<String, Void, Bitmap>() {
            protected Bitmap doInBackground(String... iUrl) {
                return downloadImage(context, cacheTime, iUrl[0], iView);
            }
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                if (iView != null) {
                    iView.setImageBitmap(result);
                }
            }
        }.execute(new String[] { url });
    }
}
