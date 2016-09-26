package com.example.customview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextView;

    private CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editTextView = (EditText) findViewById(R.id.editText);
        this.circleView = (CircleView) findViewById(R.id.view);
    }

    public void onClick(View view) {
        this.circleView.changeCircleColor(Color.RED);
        this.circleView.changeCircleText(this.editTextView.getText().toString());
    }

}
