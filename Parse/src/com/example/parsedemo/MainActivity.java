package com.example.parsedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
 
public class MainActivity extends Activity {
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;
 
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.activity_main);
        
        Parse.initialize(this,"Yzsn8WFik0XCLEhCJ4VbQyj9nxNF4s2gn3DU6OAs", "I6DvqTAaCc3XFpgwAJlvdzEORhcL0Fy1LiyVV59u");
	       
        
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
 
        // Locate Buttons in main.xml
        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
 
        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {
 
            public void onClick(View view) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();
 
                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                            	String error = "";
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(
                                            MainActivity.this,
                                            Welcome.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Login feito com sucesso!",
                                            Toast.LENGTH_LONG).show();
                                    //finish();
                                } else {
                                	switch(e.getCode()){
                					case ParseException.USERNAME_TAKEN:
                						error = "Nome de utilizador já usado, tente novamente!";
                						break;
                					case ParseException.USERNAME_MISSING:
                						error = "Tem de preencher o campo nome do utilizador!";
                						break;
                					case ParseException.PASSWORD_MISSING:
                						error = "Tem de preencher o campo palavra-chave!";
                						break;
                					case ParseException.OBJECT_NOT_FOUND:
                						error = "Credenciais inválidas, tente novamente!";
                						break;
                					default:
                						error = e.getLocalizedMessage();
                						break;
                					}
                                    Toast.makeText(
                                            getApplicationContext(),
                                            error,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        // Sign up Button Click Listener
        signup.setOnClickListener(new OnClickListener() {
 
            public void onClick(View view) {
            	if(username.getText().length() == 0 || password.getText().length() == 0)
        			return;
                                
                ParseUser user = new ParseUser();
        		user.setUsername(username.getText().toString());
        		user.setPassword(password.getText().toString());
        		
        		user.signUpInBackground(new SignUpCallback() {
        			@Override
        			public void done(ParseException e) {
        				String error = "";
        				if (e == null) {
        					Toast.makeText(getApplicationContext(),
                                    "Registo feito com sucesso! Por favor, faça login.",
                                    Toast.LENGTH_LONG).show();
        					/*Intent intent = new Intent(MainActivity.this, Welcome.class);
        					startActivity(intent);
        					finish();*/
        				} else {
        					// Sign up didn't succeed. Look at the ParseException
        					// to figure out what went wrong
        					switch(e.getCode()){
        					case ParseException.USERNAME_TAKEN:
        						error = "Nome de utilizador já usado, tente novamente!";
        						break;
        					case ParseException.USERNAME_MISSING:
        						error = "Tem de preencher o campo nome do utilizador!";
        						break;
        					case ParseException.PASSWORD_MISSING:
        						error = "Tem de preencher o campo palavra-chave!";
        						break;
        					default:
        						error = e.getLocalizedMessage();
        					}
        					Toast.makeText(getApplicationContext(),
                                    error,
                                    Toast.LENGTH_LONG).show();
        				}
        			}
        		});

            }
        });
 
    }
}