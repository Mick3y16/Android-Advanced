package com.example.parsedemo;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

public class ParseApplication extends Application{
	
	 @Override
	    public void onCreate() {
	        super.onCreate();
	 
	        // Add your initialization code here
	        Parse.initialize(this,"Yzsn8WFik0XCLEhCJ4VbQyj9nxNF4s2gn3DU6OAs", "I6DvqTAaCc3XFpgwAJlvdzEORhcL0Fy1LiyVV59u");
	       
	        ParseUser.enableAutomaticUser();
	        ParseACL defaultACL = new ParseACL();
	 
	        // If you would like all objects to be private by default, remove this line.
	        defaultACL.setPublicReadAccess(true);
	 
	        ParseACL.setDefaultACL(defaultACL, true);
	    }
	 

}
