package com.bookmystay.ui;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.bookmystay.BR;
import com.bookmystay.R;
import com.bookmystay.ViewModelProviderFactory;
import com.bookmystay.databinding.ActivityHotelDetailBinding;
import com.bookmystay.ui.base.BaseActivity;

import javax.inject.Inject;

public class HotelDetailActivity
        extends BaseActivity<ActivityHotelDetailBinding, HomeViewModel>
        implements HomeViewer {

    @Inject
    ViewModelProviderFactory factory;
    private HomeViewModel mHotelDetailViewModel;
    private ActivityHotelDetailBinding mBinding;
    private HomePagerAdapter mAdapter;
    public HomeSharedViewModel mSharedViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hotel_detail;
    }

    @Override
    public HomeViewModel getViewModel() {
        mHotelDetailViewModel =
                ViewModelProviders.of(this, factory).get(HomeViewModel.class);

        return mHotelDetailViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = getViewDataBinding();
        mHotelDetailViewModel.setNavigator(this);
        mSharedViewModel = ViewModelProviders.of(this).get(HomeSharedViewModel.class);

        mAdapter = new HomePagerAdapter(getResources(), getSupportFragmentManager(), null);
        mBinding.viewpager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewpager);
    }

}
