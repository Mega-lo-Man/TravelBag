package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class PagerAdapter extends FragmentStateAdapter {

    private List<Item> lstItems;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Item> lstItems) {
        super(fragmentActivity);
        this.lstItems = lstItems;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new SlidePageFragment(lstItems);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
