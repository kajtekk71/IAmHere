package com.example.asd.iamhere;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.LinkedList;

public class PopUpAddEditGroup extends DialogFragment {

    DataHandler handler;

    String title;
    Group group;

    int position;

    public PopUpAddEditGroup() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItem(Group group) {
        this.group = group;
    }

    public void setPosition(int position) {
        this.position = position;
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

        View alertLayout = getActivity().getLayoutInflater().inflate(R.layout.fragment_pop_up_add_edit_group, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title);
        alert.setView(alertLayout);
        final EditText groupNameEdit = (EditText) alertLayout.findViewById(R.id.group_name_edit);
        final Button okButton = (Button) alertLayout.findViewById(R.id.acceptItem);
        final Button cancelButton = (Button) alertLayout.findViewById(R.id.cancelItem);
        okButton.setEnabled(group != null);
        if (group != null) {
            groupNameEdit.setText(group.name);
        }
        groupNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                boolean isEmpty = groupNameEdit.getText().toString().trim().length() == 0;
                okButton.setEnabled(!isEmpty);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Group newGroup = new Group(groupNameEdit.getText().toString(), null);
                if (group != null) newGroup.setUsers(group.users);
                if (group != null) handler.editName(newGroup, position);
                else
                    handler.addNewGroup(new Group(groupNameEdit.getText().toString(), new LinkedList<GroupUser>()));
                getDialog().dismiss();
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


}
