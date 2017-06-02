package com.example.asd.iamhere;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity implements FirebaseUserAuthenticator {


    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
            getSupportFragmentManager().beginTransaction().replace(R.id.chatActivity,new UserPanelFragment()).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.chatActivity,new TitleFragment()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        auth.signOut();
    }

    @Override
    public void registerUser(String login, String password) {
        auth.createUserWithEmailAndPassword(login,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete())
                    Toast.makeText(ChatActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ChatActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void loginWithCredentials(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete())
                    Toast.makeText(ChatActivity.this,"Logged in successfully!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ChatActivity.this,"Failed to log in",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public String getLoggedUser() {
        if(auth.getCurrentUser()!=null)
            return auth.getCurrentUser().getEmail();
        else return "";
    }
}
