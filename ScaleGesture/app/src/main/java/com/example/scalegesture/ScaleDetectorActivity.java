package com.example.scalegesture;

import android.app.Activity;
import android.os.Bundle;

public class ScaleDetectorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ImageZoomView(this));
    }
}
