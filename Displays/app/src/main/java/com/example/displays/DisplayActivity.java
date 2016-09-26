package com.example.displays;

import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Display display = getWindowManager().getDefaultDisplay();
        setContent(findViewById(R.id.base_layout), display);
        initDisplays();
    }

    private void initDisplays() {
        DisplayManager displayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);

        if (displayManager != null) {
            Display[] displays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);

            for (Display display : displays) {
                new DisplayPresentation(this, display).show();
            }
        }
    }

    private static void setContent(View view, Display display) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        TextView pixelsTextView = (TextView) view.findViewById(R.id.device_pixels);

        if (pixelsTextView != null) {
            pixelsTextView.setText(String.format("%dx%d", displayMetrics.widthPixels, displayMetrics.heightPixels));
        }
    }

    private class DisplayPresentation extends Presentation {

        public DisplayPresentation(Context context, Display display) {
            super(context, display);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display);
            setContent(findViewById(R.id.base_layout), getDisplay());
        }
    }

}
