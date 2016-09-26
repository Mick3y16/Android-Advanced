package com.example.openglcube;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by pedro on 26/08/2016.
 */
public class CubeSurfaceView extends GLSurfaceView {

    private final CubeGLRenderer renderer;

    CubeSurfaceView(Context context) {
        super(context);

        this.renderer = new CubeGLRenderer(context);
        setRenderer(renderer);
    }
}
