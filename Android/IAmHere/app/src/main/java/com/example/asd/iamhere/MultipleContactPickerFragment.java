package com.example.asd.iamhere;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class MultipleContactPickerFragment extends Fragment {

    DataHandler handler;
    List<GroupUser> users;
    RecyclerView recyclerView;
    ContactsAdapter adapter;
    Context mContext;
    int groupNo;
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 98;

    public MultipleContactPickerFragment() {
        users = new LinkedList<>();
    }
    public void setGroup(int number){
        groupNo = number;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getContext() instanceof DataHandler)
            handler = (DataHandler) getContext();
        else handler = null;
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_contact_picker, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerContacts);
        if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            getAllContacts();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), (new LinearLayoutManager(getActivity())).getOrientation()));
        adapter = new ContactsAdapter(users, getContext());
        recyclerView.setAdapter(adapter);
        Button confirm = (Button) view.findViewById(R.id.contacts_picked_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(GroupUser user : adapter.getSelectedContacts()){
                    handler.addNewUser(groupNo,user);
                }
                removeFragment(MultipleContactPickerFragment.this);
            }
        });
        return view;
    }
    private void removeFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment).commit();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAllContacts();
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Can't get contacts without permission.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getAllContacts() {
        Hashtable<String,GroupUser> set = new Hashtable<>();
        users = new LinkedList<>();
        String contactNumber = null;
        String contactNickname = null;
        String contactName = null;
        String contactSurname = null;
        String email;
        ContentResolver cr = getContext().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Integer hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                Cursor ce = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                if (ce != null && ce.moveToFirst()) {
                    email = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    ce.close();
                } else email = null;
                if (hasPhone > 0) {
                    Cursor cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (cp != null && cp.moveToFirst()) {
                        contactNumber = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactNickname = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        contactName = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                        contactSurname = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                        cp.close();
                    }
                }
                    GroupUser user = new GroupUser(contactName, contactSurname, contactNickname, contactNumber, email);
                    set.put(user.nickname, user);


            } while (cursor.moveToNext());
            users.addAll(set.values());
            Collections.sort(users, new Comparator<GroupUser>() {
                @Override
                public int compare(GroupUser o1, GroupUser o2) {
                    return o1.nickname.compareTo(o2.nickname);
                }
            });
        }

    }
}
