package com.bookmystay.ui;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.bookmystay.R;
import com.bookmystay.ViewModelProviderFactory;
import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;
import com.bookmystay.databinding.ActivityHotelDetailBinding;
import com.bookmystay.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

public class HotelDetailActivity
        extends BaseActivity<ActivityHotelDetailBinding, HotelDetailViewModel>
        implements HotelDetailViewer {

    @Inject
    ViewModelProviderFactory factory;
    private HotelDetailViewModel mHotelDetailViewModel;
    private ActivityHotelDetailBinding mBinding;

    @Override
    public int getBindingVariable() {
        return com.bookmystay.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hotel_detail;
    }

    @Override
    public HotelDetailViewModel getViewModel() {
        mHotelDetailViewModel =
                ViewModelProviders.of(this, factory).get(HotelDetailViewModel.class);

        return mHotelDetailViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = getViewDataBinding();
        mHotelDetailViewModel.setNavigator(this);

        getViewModel().loadHotelDetails();
        getViewModel().loadComments();
    }

    @Override
    public void onHotelDetailLoaded(Hotel hotel) {
        mBinding.setHotel(hotel);
    }

    @Override
    public void onCommentsLoaded(List<Comment> comments) {
        CommentsAdapter commentsAdapter = new CommentsAdapter(comments);
        mBinding.setCommentsAdapter(commentsAdapter);
    }

    @Override
    public void handleError(Throwable throwable) {
        // TODO
    }

}
