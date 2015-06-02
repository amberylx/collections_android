package com.mycompany.myfirstapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MyActivity extends ActionBarActivity {
    public final static String EXTRA_URLS = "com.mycompany.myfirstapp.URLS";
    public final static Integer CONNECTION_TIMEOUT = 10000;
    public final static Integer DATARETRIEVAL_TIMEOUT = 10000;
    private static final String TAG = MyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        new CallAPI().execute("http://10.0.2.2:8000/api/collection/");
    }

    private static void disableConnectionReuseIfNecessary() {
        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private class CallAPI extends AsyncTask<String, String, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            ApiCaller ac = new ApiCaller(params[0]);
            return ac.callAPI();
        }

        protected void onPostExecute(JSONObject result) {
            List<JSONObject> itemlist = new ArrayList<JSONObject>();
            List<String> urllist = new ArrayList<String>();
            try {
                JSONArray array = result.getJSONArray("items");

                for (int i=0; i<array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    itemlist.add(item);

                    String url = item.getJSONArray("images").getJSONObject(0).getString("image");
                    urllist.add(url);
                }
            } catch (JSONException e) {
                System.out.println(e);
            }

            System.out.println(urllist);

            Intent intent = new Intent(getApplicationContext(), DisplayCollectionActivity.class);
            startActivity(intent);
        }
    }
}
