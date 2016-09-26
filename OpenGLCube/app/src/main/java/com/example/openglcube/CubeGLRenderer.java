package com.example.openglcube;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pedro on 26/08/2016.
 */
public class CubeGLRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private final Cube cube = new Cube();

    private long startTime;

    private long fpsStartTime;

    private long numberFrames;

    public CubeGLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        boolean SEE_THROUGH = true;

        this.startTime = System.currentTimeMillis();
        this.fpsStartTime = this.startTime;
        this.numberFrames = 0;

        // Define the lighting
        float lightAmbient[] = new float[] { 0.2f, 0.2f, 0.2f, 1 };
        float lightDiffuse[] = new float[] { 1, 1, 1, 1 };
        float[] lightPosition = new float[] { 1, 1, 1, 1 };
        gl10.glEnable(GL10.GL_LIGHTING);
        gl10.glEnable(GL10.GL_LIGHT0);
        gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
        gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
        gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition, 0);

        // What is the cube made of?
        float materialAmbient[] = new float[] { 1, 1, 1, 1 };
        float materialDiffuse[] = new float[] { 1, 1, 1, 1 };
        gl10.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, materialAmbient, 0);
        gl10.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, materialDiffuse, 0);

        // Set up any OpenGL options we need
        gl10.glEnable(GL10.GL_DEPTH_TEST);
        gl10.glDepthFunc(GL10.GL_LEQUAL);
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        if (SEE_THROUGH) {
            gl10.glDisable(GL10.GL_DEPTH_TEST);
            gl10.glEnable(GL10.GL_BLEND);
            gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
        }

        // Enable textures
        gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl10.glEnable(GL10.GL_TEXTURE_2D);

        // Load the cube's texture from a bitmap
        Cube.loadTexture(gl10, this.context, R.drawable.fca);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // Define the view frustum
        gl10.glViewport(0, 0, width, height);
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        gl10.glLoadIdentity();

        float ratio = (float) width / height;
        GLU.gluPerspective(gl10, 45.0f, ratio, 1, 100f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Clear the screen to black
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // Position model so we can see it
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();
        gl10.glTranslatef(0, 0, -3.0f);

        // Set rotation angle based on the time
        long elapsedTime = System.currentTimeMillis() - startTime;
        gl10.glRotatef(elapsedTime * (30f / 1000f), 0, 1, 0);
        gl10.glRotatef(elapsedTime * (15f / 1000f), 1, 0, 0);

        // Draw the model
        cube.draw(gl10);

        // Keep track of number of frames drawn
        this.numberFrames++;
        long fpsElapsed = System.currentTimeMillis() - fpsStartTime;
        if (fpsElapsed > 5 * 1000) { // every 5 seconds
            float fps = (this.numberFrames * 1000.0F) / fpsElapsed;
            Log.d("CubeGLRenderer", "Frames per second: " + fps + " (" + this.numberFrames + " frames in " + fpsElapsed + " ms)");
            this.fpsStartTime = System.currentTimeMillis();
            this.numberFrames = 0;
        }
    }

}
