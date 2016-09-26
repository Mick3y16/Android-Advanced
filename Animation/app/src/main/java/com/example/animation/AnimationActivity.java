package com.example.animation;

import android.app.Activity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.layout);
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(viewGroup);

                if (button.getText().equals("One")) {
                    button.setText("Two");
                } else if (button.getText().equals("Two")) {
                    button.setText("Three");
                } else {
                    button.setText("One");
                }
            }
        });
    }
}
