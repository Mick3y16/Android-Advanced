package com.example.jsoup;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupActivity extends AppCompatActivity {

    private EditText editText;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsoup);

        this.editText = (EditText) findViewById(R.id.description_edit);
        Button button = (Button) findViewById(R.id.description_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Description().execute();
            }
        });
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        private String description;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            JsoupActivity.this.progressDialog = new ProgressDialog(JsoupActivity.this);
            JsoupActivity.this.progressDialog.setTitle("Jsoup Example");
            JsoupActivity.this.progressDialog.setMessage("Loading...");
            JsoupActivity.this.progressDialog.setIndeterminate(false);
            JsoupActivity.this.progressDialog.show();
        }

        @Override
        @UiThread
        protected Void doInBackground(Void... params) {
            try {
                Document document = (Document) Jsoup.connect(JsoupActivity.this.editText.getText().toString()).get();

                // Fetch the description of the website through its meta tag.
                Elements elementsDescription = document.select("meta[name=description]");
                this.description = elementsDescription.attr("content");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Adds the description the TextView
            TextView textView = (TextView) findViewById(R.id.description_text);
            textView.setText(this.description);
            JsoupActivity.this.progressDialog.dismiss();
        }
    }

}
