package com.example.websocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class SocketTestActivity extends AppCompatActivity {

    private int messageCount = 1;

    private WebSocketConnection webSocketConnection = new WebSocketConnection();

    private EditText editTextMessage;

    private EditText editTextLog;

    private final static String WEB_SOCKET_URI = "ws://echo.websocket.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);

        this.editTextMessage = (EditText) findViewById(R.id.edit_message);
        this.editTextLog = (EditText) findViewById(R.id.edit_log);
        Button button = (Button) findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String message = SocketTestActivity.this.editTextMessage.getText().toString();
                setLog("Me: " + message);
                SocketTestActivity.this.webSocketConnection.sendTextMessage(message);
            }
        });
        start();
    }

	private void start() {
        try {
            this.webSocketConnection.connect(WEB_SOCKET_URI, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    setLog("HOST URI: " + WEB_SOCKET_URI);
                }

                @Override
                public void onTextMessage(String payload) {
                    setLog("HOST: " + payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    setLog("End of connection: " + reason);
                }
            });
        } catch (WebSocketException ex) {
            setLog(ex.toString());
        }
    }

    private void setLog(String message) {
        this.editTextLog.setText(this.editTextLog.getText().toString() + System.lineSeparator() + this.messageCount + ": " + message);
        this.messageCount++;
    }

}
