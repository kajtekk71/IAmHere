package com.example.asd.iamhere;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class ContactViewHolder extends RecyclerView.ViewHolder {
    CheckBox checkBox;
    TextView textView;
    public ContactViewHolder(View itemView) {
        super(itemView);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        textView = (TextView) itemView.findViewById(R.id.contact_text);
    }
}
