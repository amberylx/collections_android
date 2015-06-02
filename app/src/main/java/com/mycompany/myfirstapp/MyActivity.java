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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

public class MyActivity extends ActionBarActivity {
    public final static String EXTRA_BMP = "com.mycompany.myfirstapp.BMP";
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

    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    private class CallAPI extends AsyncTask<String, String, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            HttpURLConnection urlConnection = null;

            try {
                URL urlToRequest = new URL(params[0]);
                urlConnection = (HttpURLConnection)urlToRequest.openConnection();
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

                int statusCode = urlConnection.getResponseCode();
                System.out.println(statusCode);
                if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                } else if (statusCode != HttpURLConnection.HTTP_OK) {
                }

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return new JSONObject(getResponseText(in));
            } catch (MalformedURLException e) {
                System.out.println(e);
            } catch (SocketTimeoutException e) {
                System.out.println(e);
            } catch (JSONException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return null;
        }

        protected void onPostExecute(JSONObject result) {
            Intent intent = new Intent(getApplicationContext(), DisplayCollectionActivity.class);
            startActivity(intent);
        }
    }
}
