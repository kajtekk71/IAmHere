package com.example.asd.iamhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_right).replace(R.id.main, new ChooseFragment()).commit();
    }
}
