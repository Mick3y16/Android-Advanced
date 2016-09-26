package com.example.animation2;

import android.app.Activity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class AnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene1);

        // Obtain the root element of the XML layout.
        RelativeLayout firstSceneLayout = (RelativeLayout) findViewById(R.id.scene);

        // Create a new layout for the second scene.
        ViewGroup secondSceneViewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.scene2, firstSceneLayout, false);

        // Create a scene using both the root element and the viewgroup created.
        final Scene secondScene = new Scene(firstSceneLayout, secondSceneViewGroup);
        Button button = (Button) firstSceneLayout.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.go(secondScene);
            }
        });
    }
}
