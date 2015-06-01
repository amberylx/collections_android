package com.mycompany.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayCollectionActivity extends ActionBarActivity {
    private static final String TAG = MyActivity.class.getSimpleName();
    ImageAdapter ia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_collection);

        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra(MyActivity.EXTRA_BMP);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        ia = new ImageAdapter(this, bitmap);
        gridview.setAdapter(ia);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(DisplayCollectionActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        /*
        Intent intent = getIntent();
        Intent newintent = new Intent(getApplicationContext(), ImageLoad.class);
        startActivity(newintent);

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("hi");

        setContentView(textView);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void download(String url, ImageView imageView) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
        task.execute(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
