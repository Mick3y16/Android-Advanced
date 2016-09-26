package com.example.meteorology;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pedro on 31/08/2016.
 */
public class MeteorologyHTTPClient {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    private static String API_URL = "&APPID=ba7463be8ecd04425671dbb37e106aee";

    private static String IMG_URL = "http://openweathermap.org/img/w/";

    public String getWeatherData(String location) {
        HttpURLConnection httpURLConnection = null ;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) (new URL(BASE_URL + location + API_URL)).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            // Let's read the response
            StringBuffer stringBuffer = new StringBuffer();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + System.lineSeparator());
            }
            Log.i("OMG", stringBuffer.toString());

            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuffer.toString();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            } catch(Exception ex) {}
            try {
                httpURLConnection.disconnect();
            } catch(Exception ex) {}
        }

        return null;
    }

    public byte[] getImage(String code) {
        HttpURLConnection httpURLConnection = null ;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(IMG_URL + code + ".png").openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            // Let's read the response
            inputStream = httpURLConnection.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            while (inputStream.read(buffer) != -1) {
                byteArrayOutputStream.write(buffer);
            }

            return byteArrayOutputStream.toByteArray();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            } catch(Exception ex) {}
            try {
                httpURLConnection.disconnect();
            } catch(Exception ex) {}
        }

        return null;
    }

}
