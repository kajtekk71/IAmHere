package com.example.asd.iamhere;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    FirebaseUserAuthenticator handler;
    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        handler = (FirebaseUserAuthenticator) getActivity();
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        TextView tx = (TextView) view.findViewById(R.id.register_title);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "Barley.ttf");
        tx.setTypeface(custom_font);
        final EditText login = (EditText) view.findViewById(R.id.register_email);
        final EditText password = (EditText) view.findViewById(R.id.register_password);
        Button register = (Button) view.findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().toString()!="" && password.getText().toString()!="" && isValidEmailAddress(login.getText().toString()))
                    {
                        handler.registerUser(login.getText().toString(),password.getText().toString());
                        getActivity().getSupportFragmentManager().beginTransaction().remove(RegisterFragment.this).commit();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                else
                Toast.makeText(getActivity(),"Email or password is invalid.",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private boolean isValidEmailAddress(String email){
        return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

}
