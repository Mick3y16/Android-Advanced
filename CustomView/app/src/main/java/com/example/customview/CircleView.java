package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pedro on 25/08/2016.
 */
public class CircleView extends View {

    private Paint circlePaint;

    private int circleColor;

    private String circleText;

    public CircleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CircleView, 0, 0);

        try {
            this.circleText = typedArray.getString(R.styleable.CircleView_circleLabel);
            this.circleColor = typedArray.getInteger(R.styleable.CircleView_circleColor, 0);
        } finally {
            typedArray.recycle();
        }
    }

    public void changeCircleColor(int circleColor) {
        this.circleColor = circleColor;
        invalidate();
        requestLayout();
    }

    public void changeCircleText(String circleText) {
        this.circleText = circleText;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Determine the coordinates and size of the circle.
        int widthHalf = this.getMeasuredWidth() / 2;
        int heightHalf = this.getMeasuredHeight() / 2;

        int radius = (widthHalf > heightHalf) ? heightHalf - 10 : widthHalf - 10;

        // Draw the style
        this.circlePaint.setStyle(Paint.Style.FILL);
        this.circlePaint.setColor(this.circleColor);
        canvas.drawCircle(widthHalf, heightHalf, radius, this.circlePaint);

        // Draw the text
        this.circlePaint.setTextAlign(Paint.Align.CENTER);
        this.circlePaint.setTextSize(50);
        this.circlePaint.setColor(Color.WHITE);
        canvas.drawText(this.circleText, widthHalf, heightHalf, this.circlePaint);
    }
}
