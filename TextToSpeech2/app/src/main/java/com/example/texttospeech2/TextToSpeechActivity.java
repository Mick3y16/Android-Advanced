package com.example.texttospeech2;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TextToSpeechActivity extends Activity  implements TextToSpeech.OnInitListener {

    private EditText editText;

    private Button speakButton;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        this.editText = (EditText) findViewById(R.id.input_text);
        this.speakButton = (Button) findViewById(R.id.speak_button);
        this.speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertToSpeech(TextToSpeechActivity.this.editText.getText().toString());
            }
        });

        this.textToSpeech = new TextToSpeech(this, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("ERROR", "Unsupported language!");
            } else {
                convertToSpeech("Hello, write some text and press the button!");
            }
        } else {
            Log.e("ERROR", "Init failed.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.textToSpeech.shutdown();
    }

    private void convertToSpeech(String text) {
        if (text == null || text.trim().isEmpty()) {
            text = "No text was written";
        }

        this.textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}
