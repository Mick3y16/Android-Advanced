package com.example.authdroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private LoginButton loginButton;

    private Button updateStatusButton;

    private Button listFriendsButton;

    private Button postImageButton;

    private TextView infoUsernameTextView;

    private CallbackManager callbackManager;

    private AccessTokenTracker accessTokenTracker;

    private static final String MESSAGE = "Testing Facebook's SDK for Android...";

    private static final String TAG = "FACEBOOK";

    private static final List<String> READ_PERMISSIONS_LIST = Arrays.asList("user_friends");

    private static final List<String> PUBLISH_PERMISSIONS_LIST = Arrays.asList("publish_actions");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Get HashKey
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.authdroid", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } */

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        this.callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        this.infoUsernameTextView = (TextView) findViewById(R.id.user_name);
        this.loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        this.loginButton.setReadPermissions("email");
        this.loginButton.registerCallback(this.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult.getAccessToken() != null) {
                    MainActivity.this.infoUsernameTextView.setText(Profile.getCurrentProfile().getName());
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        this.accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null ) { // User logged out...
                    MainActivity.this.infoUsernameTextView.setText("Hello, anonymous user");
                }
            }
        };

        this.updateStatusButton = (Button) findViewById(R.id.update_status);
        this.listFriendsButton = (Button) findViewById(R.id.list_friends);
        this.postImageButton = (Button) findViewById(R.id.post_image);
        enableButtons(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AccessToken.getCurrentAccessToken() != null) {
            enableButtons(!AccessToken.getCurrentAccessToken().isExpired());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.accessTokenTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void postStatusMessage(View view) {
        if (checkPublishPermissions()) {
            GraphRequest postStatusMessageGraphRequest = GraphRequest.newPostRequest(
                    AccessToken.getCurrentAccessToken(), "me/feed", null, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    if (response.getError() == null) {
                        Toast.makeText(MainActivity.this,
                                "Status updated successfully",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

            Bundle parametersBundle = new Bundle();
            parametersBundle.putString("message", MESSAGE);

            postStatusMessageGraphRequest.setParameters(parametersBundle);
            postStatusMessageGraphRequest.executeAsync();
        } else {
            requestPublishPermissions();
        }
    }


    public void listFriends(View view) {
        if(checkReadPermissions()) {
            GraphRequest listFriendsGraphRequest = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
                @Override
                public void onCompleted(JSONArray objects, GraphResponse response) {
                    Log.d("JSONARRAY", objects.toString());
                    Log.d("RESPONSE", response.toString());

                    try {
                        JSONObject jsonObject = response.getJSONObject();
                        Log.d("JSONOBJECT", jsonObject.toString());
                        JSONObject summaryJsonObject = jsonObject.getJSONObject("summary");
                        Log.d("Summary", summaryJsonObject.toString());
                        Log.d("Count", summaryJsonObject.getString("total_count"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    /*
                    Log.d(TAG, "Nº de amigos: " + friends.size());
                            for (int i=0; i<friends.size();i++){
                                if(friends.get(i).getName().startsWith("R")) {
                                    Log.d("TAG","Nome: " + friends.get(i).getName());
                                }
                            }
                     */
                }
            });
            listFriendsGraphRequest.executeAsync();
        } else {
            requestReadPermisions();
        }
    }

    public void postImage(View view) {
        if (checkPublishPermissions()) {
            Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            Bundle parametersBundle = new Bundle();
            parametersBundle.putString("description", "Testing image upload...");

            GraphRequest uploadGraphRequest = GraphRequest.newUploadPhotoRequest(
                    AccessToken.getCurrentAccessToken(), null, img, MESSAGE, parametersBundle, new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            Toast.makeText(MainActivity.this, "Photo uploaded successfully", Toast.LENGTH_LONG).show();
                        }
                    });
            uploadGraphRequest.executeAsync();
        } else {
            requestPublishPermissions();
        }
    }

    private void enableButtons(boolean status) {
        this.updateStatusButton.setEnabled(status);
        this.listFriendsButton.setEnabled(status);
        this.postImageButton.setEnabled(status);
    }

    public boolean checkReadPermissions() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            return accessToken.getPermissions().contains("user_friends");
        }

        return false;
    }

    public boolean checkPublishPermissions() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            return accessToken.getPermissions().contains("publish_actions");
        }

        return false;
    }

    public void requestReadPermisions() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            LoginManager.getInstance().logInWithReadPermissions(this, READ_PERMISSIONS_LIST);
        }
    }

    public void requestPublishPermissions() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            LoginManager.getInstance().logInWithPublishPermissions(this, PUBLISH_PERMISSIONS_LIST);
        }
    }

}
