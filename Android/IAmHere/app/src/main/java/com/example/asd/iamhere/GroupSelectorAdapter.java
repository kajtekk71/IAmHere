package com.example.asd.iamhere;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class GroupSelectorAdapter extends RecyclerView.Adapter<GroupSelectorViewHolder> {
    Context mContext;
    List<Group> allGroups;
    HashSet<Group> selectedGroups;
    RecyclerViewClickHandler handler;
    public GroupSelectorAdapter(List<Group> allGroups, List<Group> selectedGroups, RecyclerViewClickHandler handler, Context context){
        mContext=context;
        this.handler = handler;
        this.allGroups = allGroups;
        this.selectedGroups = new HashSet<>(selectedGroups);
    }
    @Override
    public GroupSelectorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_selector_viewholder,null);
        final GroupSelectorViewHolder groupSelectorViewHolder = new GroupSelectorViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupSelectorViewHolder.checkBox.setChecked(!groupSelectorViewHolder.checkBox.isChecked());
            }
        });
        return groupSelectorViewHolder;
    }

    @Override
    public void onBindViewHolder(final GroupSelectorViewHolder holder, int position) {
        final Group group = allGroups.get(position);
        holder.checkBox.setText(group.name);
        holder.checkBox.setChecked(selectedGroups.contains(group));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()) {
                    selectedGroups.add(allGroups.get(holder.getAdapterPosition()));
                    handler.addGroup(allGroups.get(holder.getAdapterPosition()));
                }
                else {
                    selectedGroups.remove(allGroups.get(holder.getAdapterPosition()));
                    handler.removeGroup(allGroups.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allGroups.size();
    }

    public List<Group> getSelectedGroups(){
        return new LinkedList<>(selectedGroups);
    }
}
