package com.example.asd.iamhere;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class GroupFragment extends Fragment {

    List<Group> groups;
    GroupExpandableRecyclerAdapter adapter;
    RecyclerView recycler;
    final static String filename = "groups";

    public GroupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        groups = readItemsFromFile();
        adapter = new GroupExpandableRecyclerAdapter(recycler.getContext(), this, groups);
        recycler.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpAddEditGroup popup = new PopUpAddEditGroup();
                popup.setTitle("Add new group");
                popup.setItem(null);
                popup.show(getActivity().getSupportFragmentManager(), "popup");
            }
        });
        return view;
    }
    @Override
    public void onPause(){
        super.onPause();
        saveItemsToFile();
    }
    public void addGroup(Group group) {
        groups.add(group);
        adapter.notifyParentDataSetChanged(false);
        saveItemsToFile();
    }

    public void addUser(int position, GroupUser user) {
        groups.get(position).addUser(user);
        adapter.notifyParentDataSetChanged(false);
        saveItemsToFile();
    }

    public void replaceUser(GroupUser user, int parent, int child) {
        groups.get(parent).users.set(child, user);
        adapter.notifyParentDataSetChanged(false);
        saveItemsToFile();
    }

    public void replaceGroup(Group group, int index) {
        groups.set(index, group);
        adapter.notifyParentDataSetChanged(false);
        saveItemsToFile();
    }
    public ArrayList<Group> readItemsFromFile(){
        ArrayList<Group> groups = new ArrayList<>();
        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = getContext().openFileInput(filename);
            in = new ObjectInputStream(fis);
            while(true) {
                Group group = (Group) in.readObject();
                if(group!=null) groups.add(group);
            }
        } catch (Exception ex) {
            return groups;
        }
    }
    public void saveItemsToFile(){
        FileOutputStream fos;
        ObjectOutputStream out;
        try {
            fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fos);
            for(Serializable item: groups)
                out.writeObject(item);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
