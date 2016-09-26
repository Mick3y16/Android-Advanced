package com.example.opengltriangle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by pedro on 26/08/2016.
 */
public class TriangleGLSurfaceView extends GLSurfaceView {

    private final TriangleGLRenderer triangleGLRenderer;

    private float previousX;

    private float previousY;

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;

    public TriangleGLSurfaceView(Context context) {
        super(context);

        // Context for OpenGL ES 2.0.
        setEGLContextClientVersion(2);

        // Defines the configuration.
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        // Defines the renderer to draw on the surface.
        this.triangleGLRenderer = new TriangleGLRenderer();
        setRenderer(this.triangleGLRenderer);

        // Renders the view only when there is an update.
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - this.previousX;
                float dy = y - this.previousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                this.triangleGLRenderer.setAngle(
                        this.triangleGLRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                requestRender();
        }

        this.previousX = x;
        this.previousY = y;
        return true;
    }
}
