package com.example.asd.iamhere;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.asd.iamhere.MultipleContactPickerFragment.PERMISSIONS_REQUEST_READ_CONTACTS;

public class TitleFragment extends Fragment {


    FirebaseUserAuthenticator handler;
    public TitleFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        handler = (FirebaseUserAuthenticator)getActivity();
        TextView tx = (TextView) view.findViewById(R.id.appTitleMain);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "Barley.ttf");
        tx.setTypeface(custom_font);
        final EditText login = (EditText)view.findViewById(R.id.loginBox);
        final EditText password = (EditText)view.findViewById(R.id.passwordBox);
        Button signUp = (Button)view.findViewById(R.id.signUpButton);
        Button signIn = (Button)view.findViewById(R.id.signInButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.chatActivity,new RegisterFragment()).addToBackStack(null).commit();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().toString()!="" && isValidEmailAddress(login.getText().toString()) && password.getText().toString()!="")
                {
                    handler.loginWithCredentials(login.getText().toString(),password.getText().toString());
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.chatActivity,new UserPanelFragment()).commit();
                }
                else
                    Toast.makeText(getActivity(),"Type valid email and password.",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private boolean isValidEmailAddress(String email){
        return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

}
