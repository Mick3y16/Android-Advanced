package com.example.translucentui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        String[] osValues = new String[] {"Android", "iPhone", "WindowsMobile", "Blackberry",
                "WebOS", "Ubuntu", "Windows7", "Mac OS X", "Linux", "OS/2", ""};
        List<String> osList = Arrays.asList(osValues);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osList);

        ((ListView) findViewById(R.id.list)).setAdapter(arrayAdapter);
    }
}
