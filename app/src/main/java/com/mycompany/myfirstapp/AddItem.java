package com.mycompany.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItem extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void addApiSubmit(View view) {
        String title = "";
        String text = "";
        title = ((EditText)findViewById(R.id.item_title)).getText().toString();
        text = ((EditText)findViewById(R.id.item_text)).getText().toString();
        new CallAPI().execute("http://10.0.2.2:8000/api/item_add/", title, text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    private class CallAPI extends AsyncTask<String, String, JSONObject> {
        HashMap<String, String> postParameters = new HashMap<String, String>();

        @Override
        protected JSONObject doInBackground(String... params) {
            postParameters.put("title", params[1]);
            postParameters.put("text", params[2]);
            postParameters.put("collection", "1");
            postParameters.put("times_collected", "1");
            postParameters.put("active", "1");
            ApiCaller ac = new ApiCaller(params[0], postParameters);
            return ac.makePostRequest();
        }

        protected void onPostExecute(JSONObject result) {
            System.out.println("******onpostexecute");
            /*
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

            Intent intent = new Intent(getApplicationContext(), DisplayCollectionActivity.class);
            startActivity(intent);*/
        }
    }
}
