package com.example.asd.iamhere;


import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserPanelFragment extends Fragment {

    ViewPager pager;
    UserPanelPagerAdapter adapter;
    FirebaseUserAuthenticator handler;
    public UserPanelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler = (FirebaseUserAuthenticator) getActivity();
        View view = inflater.inflate(R.layout.fragment_user_panel, container, false);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.userpanel_toolbar);
        toolbar.setTitle(handler.getLoggedUser());
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.userpanel_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Messages"));
        tabLayout.addTab(tabLayout.newTab().setText("Invitations"));
        pager = (ViewPager)view.findViewById(R.id.userpanel_pager);
        adapter = new UserPanelPagerAdapter(getFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

}
