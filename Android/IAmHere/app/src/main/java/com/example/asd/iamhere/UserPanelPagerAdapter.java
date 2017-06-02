package com.example.asd.iamhere;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

public class UserPanelPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    SparseArrayCompat<Fragment> registeredFragments = new SparseArrayCompat<>();
    public UserPanelPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MessagesFragment();
            case 1:
                return new InvitesFragment();
            default:
                return null;
        }
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position,fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
    public Fragment getRegisteredFragment(int position){
        return registeredFragments.get(position);
    }
    @Override
    public int getCount() {
        return tabCount;
    }
}
