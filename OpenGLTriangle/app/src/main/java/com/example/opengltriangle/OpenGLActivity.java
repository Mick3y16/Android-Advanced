package com.example.opengltriangle;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenGLActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creates an instance of GLSurfaceView and sets it as the content view.
        this.glSurfaceView = new TriangleGLSurfaceView(this);
        setContentView(this.glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stops the rendering thread for memory saving.
        this.glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Brings back the rendering thread.
        this.glSurfaceView.onResume();
    }

}
