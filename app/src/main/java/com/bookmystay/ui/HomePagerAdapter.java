package com.bookmystay.ui;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bookmystay.R;
import com.bookmystay.ui.hotelDetail.HotelDetailFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private final Resources resources;
    private Bundle bundle;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public HomePagerAdapter(final Resources resources, final FragmentManager fragmentManager,
                            Bundle bundle) {
        super(fragmentManager);
        this.resources = resources;
        this.bundle = bundle;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new HotelDetailFragment();
                break;
            case 1:
                fragment = new PostCommentFragment();
                break;
            default:
                fragment = new HotelDetailFragment();
                break;
        }
        Bundle bundle;
        if (this.bundle == null) {
            bundle = new Bundle();
        } else {
            bundle = new Bundle(this.bundle);
        }
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.comment);
            case 1:
                return resources.getString(R.string.hotel_details);
            default:
                return null;
        }
    }

}
