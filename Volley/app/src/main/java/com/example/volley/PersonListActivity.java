package com.example.volley;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonListActivity extends ListActivity {

    private static final String URL = "https://dl.dropboxusercontent.com/u/9026625/pessoas.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL, null, new ResponseListener(), new ErrorListener());
        requestQueue.add(jsonObjectRequest);
    }

    private class ResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            List<Person> personList = new ArrayList<>();

            try {
                JSONObject jsonPersonObject = response.getJSONObject("pessoas");
                JSONArray jsonPersonArray = jsonPersonObject.getJSONArray("pessoa");

                for (int personID = 0; personID < jsonPersonArray.length(); personID++) {
                    JSONObject jsonObject = jsonPersonArray.getJSONObject(personID);

                    String name = jsonObject.getString("nome");
                    String imageURL = jsonObject.getString("url_foto").replace("http://", "https://");

                    Person person = new Person(name, imageURL);
                    personList.add(person);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            setListAdapter(new PersonAdapter(PersonListActivity.this, personList));
        }

    }

    private class ErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(PersonListActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }

    }

}
