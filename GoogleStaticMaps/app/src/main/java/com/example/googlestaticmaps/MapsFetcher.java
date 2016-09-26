package com.example.googlestaticmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by pedro on 04/09/2016.
 */
public class MapsFetcher extends AsyncTask<ImageView, Void, Bitmap> {

    private String address;

    private int mapWidth;

    private int mapHeight;

    private String mapType;

    private List<String> mapMarkers;

    private ImageView imageView;

    public static final String BASE_URL = "http://maps.googleapis.com/maps/api/staticmap";

    public static final String API_KEY = "AIzaSyBb5fmMeA64sijp_CouWItu9rlRCHeqUOo";

    public static final String UTF8 = "UTF-8";

    private static final String TAG = "MapsFetcher";

    public MapsFetcher(String address, int mapWidth, int mapHeight, String mapType, List<String> mapMarkers) {
        this.address = address;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapType = mapType;
        this.mapMarkers = mapMarkers;
    }

    @Override
    protected Bitmap doInBackground(ImageView... params) {
        HttpURLConnection httpURLConnection = null;
        this.imageView = params[0];

        try {
            StringBuilder stringBuilder = new StringBuilder("?");
            if (this.address != null) {
                stringBuilder.append("center=").append(URLEncoder.encode(this.address, UTF8)).append("&");

            }

            if (this.mapWidth > 0 && this.mapHeight > 0) {
                stringBuilder.append("size=").append(String.format("%dx%d", this.mapWidth, this.mapHeight)).append("&");
            }

            if (this.mapType != null) {
                stringBuilder.append("maptype=").append(this.mapType).append("&");
            }

            if (this.mapMarkers != null) {
                for (String marker : this.mapMarkers) {
                    stringBuilder.append("markers=").append(URLEncoder.encode(marker, UTF8));
                }
            }

            if (API_KEY != null) {
                stringBuilder.append("key=").append(API_KEY).append("&");
            }

            stringBuilder.append("sensor=false");
            Log.i("URL", stringBuilder.toString());

            URL url = new URL(BASE_URL + stringBuilder.toString());
            // TEST URL
            //URL url = new URL("http://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Ccolor:red%7Clabel:C%7C40.718217,-73.998284&sensor=false");

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode()	== HttpURLConnection.HTTP_OK) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                return BitmapFactory.decodeStream(bufferedInputStream);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error fetching map!", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        this.imageView.setImageBitmap(bitmap);
    }

    public class MapType {

        public static final String ROADMAP = "roadmap";
        public static final String SATELLITE = "satellite";
        public static final String TERRAIN = "terrain";
        public static final String HYBRID = "hybrid";

    }
}
