package com.example.asd.iamhere;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class DialogGroupSelectorFragment extends DialogFragment implements MultipleGroupSelectCallback , RecyclerViewClickHandler {

    private List<Group> oldGroups;
    private List<Group> allGroups;
    private List<Group> newGroups;
    RecyclerView recyclerView;
    GroupSelectorAdapter adapter;

    public void setGroups(List<Group> oldGroups, List<Group> allGroups){
        this.oldGroups = oldGroups;
        this.allGroups = allGroups;
        this.newGroups = new LinkedList<>(oldGroups);
    }
    public DialogGroupSelectorFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_group_selector, container, false);
        Button cancelButton = (Button) view.findViewById(R.id.button_selector_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button okButton = (Button) view.findViewById(R.id.button_selector_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MultipleGroupSelectCallback)getTargetFragment()).passDataBackToFragment(adapter.getSelectedGroups());
                getDialog().dismiss();
            }
        });
        recyclerView = (RecyclerView)view.findViewById(R.id.group_selector_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new GroupSelectorAdapter(allGroups,newGroups,this,getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void passDataBackToFragment(List<Group> selectedGroups) {
        this.newGroups = selectedGroups;
    }

    @Override
    public void addGroup(Group group) {
        this.newGroups.add(group);
    }

    @Override
    public void removeGroup(Group group) {
        this.newGroups.remove(group);
    }
}
