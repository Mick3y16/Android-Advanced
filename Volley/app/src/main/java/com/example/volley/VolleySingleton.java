package com.example.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by pedro on 02/09/2016.
 */
public class VolleySingleton {

    private static VolleySingleton INSTANCE;

    // Request line.
    private RequestQueue requestQueue;

    // Image loader.
    private ImageLoader imageLoader;

    private VolleySingleton(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<>(10);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public RequestQueue getRequestQueue() {
        return this.requestQueue;
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }

    public static VolleySingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VolleySingleton(context);
        }

        return INSTANCE;
    }

}
