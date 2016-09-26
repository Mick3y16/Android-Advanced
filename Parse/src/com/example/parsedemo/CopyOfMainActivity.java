package com.example.parsedemo;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class CopyOfMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 Parse.initialize(this,"Yzsn8WFik0XCLEhCJ4VbQyj9nxNF4s2gn3DU6OAs", "I6DvqTAaCc3XFpgwAJlvdzEORhcL0Fy1LiyVV59u");
	       
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		
		// Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(currentUser)) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
           
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                Intent intent = new Intent(this, Welcome.class);
                startActivity(intent);
                finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
 
	}

	
}
