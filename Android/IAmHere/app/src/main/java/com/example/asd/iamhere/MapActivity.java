package com.example.asd.iamhere;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity implements DataHandler {
    MapMainFragment mapMainFragment;
    GroupFragment groupFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapMainFragment = new MapMainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.map_activity,mapMainFragment).commit();
    }

    @Override
    public void addNewUser(int position, GroupUser user) {
        groupFragment = (GroupFragment) mapMainFragment.getMapPagerAdapter().getRegisteredFragment(mapMainFragment.getPager().getCurrentItem());
        groupFragment.addUser(position, user);
    }

    @Override
    public void replaceUser(GroupUser user, int parent, int child) {
        groupFragment = (GroupFragment) mapMainFragment.getMapPagerAdapter().getRegisteredFragment(mapMainFragment.getPager().getCurrentItem());
        groupFragment.replaceUser(user, parent, child);
    }

    @Override
    public void addNewGroup(Group group) {
        groupFragment = (GroupFragment) mapMainFragment.getMapPagerAdapter().getRegisteredFragment(mapMainFragment.getPager().getCurrentItem());
        groupFragment.addGroup(group);
    }

    @Override
    public void editName(Group group, int position) {
        groupFragment = (GroupFragment) mapMainFragment.getMapPagerAdapter().getRegisteredFragment(mapMainFragment.getPager().getCurrentItem());
        groupFragment.replaceGroup(group, position);
    }
}
