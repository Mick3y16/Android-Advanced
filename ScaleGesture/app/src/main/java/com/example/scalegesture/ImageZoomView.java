package com.example.scalegesture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by pedro on 26/08/2016.
 */
public class ImageZoomView extends View {

    private Drawable image;

    private float scaleFactor = 1.0f;

    private ScaleGestureDetector scaleGestureDetector;

    public ImageZoomView(Context context) {
        super(context);
        setFocusable(true);

        this.image = context.getResources().getDrawable(R.drawable.castle);
        this.image.setBounds(0, 0, this.image.getIntrinsicWidth(), this.image.getIntrinsicHeight());
        this.scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Defines the limits of the image.
        canvas.save();
        canvas.scale(this.scaleFactor, this.scaleFactor);
        this.image.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.scaleGestureDetector.onTouchEvent(event);
        invalidate();
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            ImageZoomView.this.scaleFactor *= detector.getScaleFactor();

            // Limits the min/max image size.
            ImageZoomView.this.scaleFactor = Math.max(0.1f, Math.min(ImageZoomView.this.scaleFactor , 5.0f));

            invalidate();
            return true;
        }

    }

}
