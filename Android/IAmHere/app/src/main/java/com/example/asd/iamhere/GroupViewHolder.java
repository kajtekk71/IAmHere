package com.example.asd.iamhere;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

public class GroupViewHolder extends ParentViewHolder {
    public View view;
    public ImageView mGroupIcon;
    public TextView mGroupName;

    public GroupViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        mGroupIcon = (ImageView) itemView.findViewById(R.id.group_ico);
        mGroupName = (TextView) itemView.findViewById(R.id.group_title);
    }

}
