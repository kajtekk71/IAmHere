package com.example.asd.iamhere;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<GroupUser> list;
    private HashSet<Integer> indexes;
    private Context mContext;
    public ContactsAdapter(List<GroupUser> list, Context context){
        mContext = context;
        this.list = list;
        indexes = new HashSet<>();
    }
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_viewholder,null);
        final ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactViewHolder.checkBox.setChecked(!contactViewHolder.checkBox.isChecked());
                if(contactViewHolder.checkBox.isChecked()) indexes.add(contactViewHolder.getAdapterPosition());
                else indexes.remove(contactViewHolder.getAdapterPosition());
            }
        });
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        final GroupUser groupUser = list.get(position);
        holder.checkBox.setChecked(false);
        holder.textView.setText(groupUser.nickname);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<GroupUser> getSelectedContacts(){
        List<GroupUser> selectedUsers = new LinkedList<>();
        for(Integer ind : indexes){
            selectedUsers.add(list.get(ind));
        }
        return selectedUsers;
    }
}
