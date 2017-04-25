package com.example.okti.appan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ImageButton btn_go = (ImageButton) findViewById(R.id.sentbut);
        btn_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(
                        Message.this,
                        Sent.class);
                startActivity(i);
            }

        });

    }
}
