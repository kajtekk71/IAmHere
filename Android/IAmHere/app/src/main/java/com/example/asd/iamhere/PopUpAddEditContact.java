package com.example.asd.iamhere;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PopUpAddEditContact extends DialogFragment {


    DataHandler handler;
    String title;
    GroupUser user;

    int childPosition;
    int parentPosition;

    public PopUpAddEditContact() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop_up_add_edit_contact, container, false);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItem(GroupUser user) {
        this.user = user;
    }

    public void setPositionParent(int position) {
        this.parentPosition = position;
    }

    public void setPositionChild(int position) {
        this.childPosition = position;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataHandler) {
            handler = (DataHandler) context;
        }
    }

    @Override
    public void onDetach() {
        handler = null;
        super.onDetach();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View alertLayout = getActivity().getLayoutInflater().inflate(R.layout.fragment_pop_up_add_edit_contact, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title);
        alert.setView(alertLayout);
        final EditText nicknameEdit = (EditText) alertLayout.findViewById(R.id.nickname_edit);
        final EditText nameEdit = (EditText) alertLayout.findViewById(R.id.name_edit);
        final EditText surnameEdit = (EditText) alertLayout.findViewById(R.id.surname_edit);
        final EditText phoneNumberEdit = (EditText) alertLayout.findViewById(R.id.phone_edit);
        final EditText emailEdit = (EditText) alertLayout.findViewById(R.id.email_edit);
        final Button okButton = (Button) alertLayout.findViewById(R.id.acceptItem);
        final Button cancelButton = (Button) alertLayout.findViewById(R.id.cancelItem);
        if (user != null) {
            nicknameEdit.setText(user.nickname);
            nameEdit.setText(user.name);
            surnameEdit.setText(user.surname);
            phoneNumberEdit.setText(user.phoneNo);
            emailEdit.setText(user.email);
        }
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (canAdd(nicknameEdit.getText().toString(), phoneNumberEdit.getText().toString(), emailEdit.getText().toString())) {
                    GroupUser newUser = new GroupUser(nameEdit.getText().toString(), surnameEdit.getText().toString(), nicknameEdit.getText().toString(), phoneNumberEdit.getText().toString(), emailEdit.getText().toString());
                    if (user != null) handler.replaceUser(newUser, parentPosition, childPosition);
                    else handler.addNewUser(parentPosition, newUser);
                    getDialog().dismiss();
                }
                else Toast.makeText(getContext(),"Nickname cannot be empty. Add valid phone number or e-mail address.",Toast.LENGTH_LONG).show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return alert.create();
    }
    public boolean canAdd(String nickname, String number, String email){
        return !isNullOrEmpty(nickname) && (isValidPhoneNumber(number) || isValidEmailAddress(email));
    }
    public boolean isValidPhoneNumber(String number){
        return number.matches("^\\d{9}$");
    }
    public boolean isValidEmailAddress(String email){
        return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }
    public boolean isNullOrEmpty(String string){
        return string==null || string.isEmpty();
    }

}
