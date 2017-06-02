package com.example.asd.iamhere;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

import static com.example.asd.iamhere.MultipleContactPickerFragment.PERMISSIONS_REQUEST_READ_CONTACTS;

public class GroupExpandableRecyclerAdapter extends ExpandableRecyclerAdapter<Group, GroupUser, GroupViewHolder, GroupUserViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<Group> mList;
    private Fragment mFragment;


    public GroupExpandableRecyclerAdapter(Context context, Fragment fragment, @NonNull List<Group> parentList) {
        super(parentList);
        mList = parentList;
        mContext = context;
        mFragment = fragment;
        layoutInflater = LayoutInflater.from(mContext);
    }

    private void deleteFromCollection(int parentPosition, int childPosition) {
        mList.get(parentPosition).users.remove(childPosition);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        return new GroupViewHolder(layoutInflater.inflate(R.layout.group_header, parentViewGroup, false));
    }

    @NonNull
    @Override
    public GroupUserViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        return new GroupUserViewHolder(layoutInflater.inflate(R.layout.group_user, childViewGroup, false));
    }

    @Override
    public void onBindParentViewHolder(@NonNull final GroupViewHolder parentViewHolder, final int parentPosition, @NonNull final Group parent) {
        Group group = parent;
        parentViewHolder.mGroupName.setText(group.getName());
        parentViewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, parentViewHolder.view);
                popupMenu.inflate(R.menu.menu_parent);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.parent_edit:
                                PopUpAddEditGroup addEditGroup = new PopUpAddEditGroup();
                                addEditGroup.setPosition(parentPosition);
                                addEditGroup.setTitle("Edit group");
                                addEditGroup.setItem(parent);
                                addEditGroup.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "editGroup");
                                break;
                            case R.id.parent_delete:
                                mList.remove(parentPosition);
                                notifyParentRemoved(parentPosition);
                                break;
                            case R.id.add_user:
                                PopUpAddEditContact addEditContact = new PopUpAddEditContact();
                                addEditContact.setPositionParent(parentPosition);
                                addEditContact.setPositionChild(0);
                                addEditContact.setTitle("Add new user");
                                addEditContact.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "addUser");
                                break;
                            case R.id.add_from_contacts:
                                if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                                    ActivityCompat.requestPermissions((AppCompatActivity)mContext,
                                        new String[]{Manifest.permission.READ_CONTACTS},
                                        PERMISSIONS_REQUEST_READ_CONTACTS);
                                    MultipleContactPickerFragment multipleContactPickerFragment = new MultipleContactPickerFragment();
                                    multipleContactPickerFragment.setGroup(parentPosition);
                                    mFragment.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.map_activity, multipleContactPickerFragment).addToBackStack(null).commit();
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public void onBindChildViewHolder(@NonNull GroupUserViewHolder childViewHolder, final int parentPosition, final int childPosition, @NonNull GroupUser child) {
        final GroupUser user = child;
        childViewHolder.mNickname.setText(user.nickname);
        childViewHolder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, view);
                popupMenu.inflate(R.menu.more_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.details:
                                PopUpDetails details = new PopUpDetails();
                                details.setUser(user);
                                details.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "details");
                                break;
                            case R.id.edit:
                                PopUpAddEditContact addEditContact = new PopUpAddEditContact();
                                addEditContact.setItem(user);
                                addEditContact.setTitle("Edit user");
                                addEditContact.setPositionParent(parentPosition);
                                addEditContact.setPositionChild(childPosition);
                                addEditContact.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "editUser");
                                break;
                            case R.id.delete:
                                deleteFromCollection(parentPosition, childPosition);
                                notifyChildRemoved(parentPosition, childPosition);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
