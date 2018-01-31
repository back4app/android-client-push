package com.back4app.clientpush;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Parse.initialize(this);
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        // don't forget to change the line below with the sender ID you obtained at Firebase
        installation.put("GCMSenderId", "790565434350");
        installation.saveInBackground();



        final HashMap<String, String> params = new HashMap<>();


        final Button client_push_button = findViewById(R.id.client_push_button);
        client_push_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Setting up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(MainActivity.this);
                dlg.setTitle("Please, wait a moment.");
                dlg.setMessage("Seding push...");
                dlg.show();


                ParseCloud.callFunctionInBackground("pushsample", params, new FunctionCallback<Object>() {
                    @Override
                    public void done(Object response, ParseException exc) {
                        if(exc == null) {
                            // The function executed, but still has to check the response
                            dlg.dismiss();
                            alertDisplayer("Successful Push","Check on your phone the notifications to confirm!");
                        }
                        else {
                            // Something went wrong
                            dlg.dismiss();
                            Toast.makeText(MainActivity.this, exc.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}