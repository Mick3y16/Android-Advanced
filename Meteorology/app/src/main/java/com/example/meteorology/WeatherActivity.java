package com.example.meteorology;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.example.meteorology.model.MeteorologyData;

import org.json.JSONObject;

import java.io.InputStreamReader;

public class WeatherActivity extends AppCompatActivity {

    private EditText location;

    private TextView description;

    private TextView temperature;

    private TextView humidity;

    private ImageView weatherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        this.location = (EditText) findViewById(R.id.location);
        this.description = (TextView) findViewById(R.id.description);
        this.temperature = (TextView) findViewById(R.id.temperature);
        this.humidity = (TextView) findViewById(R.id.humidity);
        this.weatherImage = (ImageView) findViewById(R.id.weatherImage);
    }

    public void watchWeather(View view) {
        new MeteorologyTask().execute(new String[] { this.location.getText().toString() });
    }

    private class MeteorologyTask extends AsyncTask<String, Void, MeteorologyData> {

        @Override
        protected MeteorologyData doInBackground(String... strings) {
            MeteorologyData meteorologyData = new MeteorologyData();
            String data = new MeteorologyHTTPClient().getWeatherData(strings[0]);

            JsonObject jsonObject = Json.parse(data).asObject();
            JsonArray jsonArray = jsonObject.get("weather").asArray();

            meteorologyData.setId(jsonArray.get(0).asObject().get("id").asInt());
            meteorologyData.setDescription(jsonArray.get(0).asObject().get("description").asString());
            meteorologyData.setIcon(jsonArray.get(0).asObject().get("icon").asString());
            meteorologyData.iconData = new MeteorologyHTTPClient().getImage(meteorologyData.getIcon());
            meteorologyData.setTemperature(jsonObject.get("main").asObject().get("temp").asFloat());
            meteorologyData.setHumidity(jsonObject.get("main").asObject().get("humidity").asFloat());

            return meteorologyData;
        }

        @Override
        protected void onPostExecute(MeteorologyData meteorologyData) {
            super.onPostExecute(meteorologyData);

            if(meteorologyData.getIcon()!=null && meteorologyData.getIcon().length() > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(meteorologyData.iconData, 0, meteorologyData.iconData.length);
                WeatherActivity.this.weatherImage.setImageBitmap(bitmap);
            }

            WeatherActivity.this.description.setText("Description: " + meteorologyData.getDescription());
            WeatherActivity.this.temperature.setText("Temperature: " + Math.round((meteorologyData.getTemperature() - 273.15)) + "º");
            WeatherActivity.this.humidity.setText("Humidity: " + meteorologyData.getHumidity() + "%");
        }
    }

}
