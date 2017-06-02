package com.example.asd.iamhere;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

public class GroupUserViewHolder extends ChildViewHolder {

    public TextView mNickname;
    public ImageButton mMore;

    public GroupUserViewHolder(View itemView) {
        super(itemView);

        mNickname = (TextView) itemView.findViewById(R.id.group_username);
        mMore = (ImageButton) itemView.findViewById(R.id.item_menu);
    }

}
