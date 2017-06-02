package com.example.asd.iamhere;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

public class GroupSelectorViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;
    public GroupSelectorViewHolder(View itemView) {
        super(itemView);
        checkBox = (CheckBox)itemView.findViewById(R.id.group_selector_checkbox);
    }
}
