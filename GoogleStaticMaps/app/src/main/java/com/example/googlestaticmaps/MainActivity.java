package com.example.googlestaticmaps;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MapsFetcher("porto,portugal", 200, 200, MapsFetcher.MapType.SATELLITE, null).execute((ImageView) findViewById(R.id.image_view_map));
    }

}
