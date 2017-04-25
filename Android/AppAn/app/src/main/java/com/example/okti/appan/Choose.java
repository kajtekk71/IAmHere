package com.example.okti.appan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        final ImageButton button = (ImageButton) findViewById(R.id.fac);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                Intent i = new Intent(
                        getApplicationContext(),
                        Friends.class);
                i.putExtra("fac",true);
                startActivity(i);
            }
        });
        final ImageButton button2 = (ImageButton) findViewById(R.id.sms);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                Intent i = new Intent(
                        getApplicationContext(),
                        Friends.class);
                i.putExtra("sms",true);
                startActivity(i);
            }
        });
        final ImageButton button3 = (ImageButton) findViewById(R.id.mail);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                Intent i = new Intent(
                        getApplicationContext(),
                        Friends.class);
                i.putExtra("mail",true);
                startActivity(i);
            }
        });

    }
}
