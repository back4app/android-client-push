package com.back4app.clientpush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this);
        final ArrayList<String> channels = new ArrayList<>();
        channels.add("News");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        // don't forget to change the line below with the sender ID you obtained at Firebase
        installation.put("GCMSenderId", "790565434350");
        installation.put("channels", channels);
        installation.saveInBackground();

        final Button client_push_button = findViewById(R.id.client_push_button);
        client_push_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject data = new JSONObject();

                try {
                    data.put("alert", "Back4App Rocks!");
                    data.put("title", "Hello from Device");
                } catch ( JSONException e) {
                    // should not happen
                    throw new IllegalArgumentException("unexpected parsing error", e);
                }

                ParsePush push = new ParsePush();
                push.setChannel("News");
                push.setData(data);
                push.sendInBackground();
            }
        });
    }
}