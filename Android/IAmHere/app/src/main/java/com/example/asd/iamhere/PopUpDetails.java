package com.example.asd.iamhere;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PopUpDetails extends DialogFragment {

    GroupUser user;

    public PopUpDetails() {
    }

    public void setUser(GroupUser user) {
        this.user = user;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View alertLayout = getActivity().getLayoutInflater().inflate(R.layout.fragment_pop_up_details, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Details");
        alert.setView(alertLayout);

        TextView nickname = (TextView) alertLayout.findViewById(R.id.nickname_info);
        TextView name = (TextView) alertLayout.findViewById(R.id.name_info);
        TextView surname = (TextView) alertLayout.findViewById(R.id.surname_info);
        TextView phone = (TextView) alertLayout.findViewById(R.id.phone_info);
        TextView email = (TextView) alertLayout.findViewById(R.id.email_info);
        Button okButton = (Button) alertLayout.findViewById(R.id.details_ok);

        nickname.setText(user.nickname);
        name.setText(user.name);
        surname.setText(user.surname);
        phone.setText(user.phoneNo);
        email.setText(user.email);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return alert.create();
    }
}
