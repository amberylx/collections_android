package com.mycompany.myfirstapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private static final String[] URLS = {
            "http://10.0.2.2:8000/static/item_images/screenshot004.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot005.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot006.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot007.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot008.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot010.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot017.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot019.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot015.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot020.200x200.jpg",
            "http://10.0.2.2:8000/static/item_images/screenshot016.200x200.jpg"
    };
    private final ImageDownloader imageDownloader = new ImageDownloader();

    public int getCount() {
        return URLS.length;
    }

    public Object getItem(int position) {
        return URLS[position];
    }

    public long getItemId(int position) {
        return URLS[position].hashCode();
    }

    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = new ImageView(parent.getContext());
            view.setLayoutParams(new GridView.LayoutParams(200, 200));
            view.setPadding(8, 8, 8, 8);
        }
        imageDownloader.download(URLS[position], (ImageView)view);

        return view;
    }

    public ImageDownloader getImageDownloader() {
        return imageDownloader;
    }
}