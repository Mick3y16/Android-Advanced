package com.example.opengltriangle;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pedro on 26/08/2016.
 */
public class TriangleGLRenderer implements GLSurfaceView.Renderer {

    private Triangle triangle;

    private float angle;

    // MVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mvpMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];

    public float getAngle() {
        return this.angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // Defines the background color.
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // Creates the graphic object to draw.
        this.triangle = new Triangle();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] scratch = new float[16];

        // Redraws the background color.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        // Defines the position of the camera (View Matrix).
        Matrix.setLookAtM(this.mvpMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculates the projection and view transformation.
        Matrix.multiplyMM(this.mvpMatrix, 0, this.projectionMatrix, 0, this.viewMatrix, 0);
        // Creates the rotation for the triangle.
        Matrix.setRotateM(scratch, 0, this.angle, 0, 0, 1.0f);
        // Draws the triangle.
        this.triangle.draw(scratch);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // Adjust the viewport based on the changes and geometry of the view.
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // The projection matrix is applied to the object in the onDrawFrame method.
        Matrix.frustumM(this.projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("TriangleGLRenderer", glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}
