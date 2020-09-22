package com.bookmystay.ui.hotelDetail;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.bookmystay.BR;
import com.bookmystay.R;
import com.bookmystay.ViewModelProviderFactory;
import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;
import com.bookmystay.databinding.FragmentHotelDetailBinding;
import com.bookmystay.ui.HomeSharedViewModel;
import com.bookmystay.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HotelDetailFragment
        extends BaseFragment<FragmentHotelDetailBinding, HotelDetailViewModel>
        implements HotelDetailViewer {

    @Inject
    ViewModelProviderFactory factory;
    private HomeSharedViewModel mHomeSharedViewModel;
    private CommentsAdapter mCommentsAdapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hotel_detail;
    }

    @Override
    public HotelDetailViewModel getViewModel() {
        mViewModel =
                ViewModelProviders.of(this, factory).get(HotelDetailViewModel.class);

        return mViewModel;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() == null) {
            return;
        }
        mViewModel.setNavigator(this);
        mHomeSharedViewModel = ViewModelProviders.of(getActivity(), factory).get(HomeSharedViewModel.class);
        mHomeSharedViewModel.mComments.observe(this, this::updateCommentsList);
        getViewModel().loadHotelDetails();
        getViewModel().loadComments();
    }

    @Override
    public void onHotelDetailLoaded(Hotel hotel) {
        mBinding.setHotel(hotel);
    }

    @Override
    public void onCommentsLoaded(List<Comment> comments) {
        mHomeSharedViewModel.mComments.postValue(new ArrayList<>(comments));
    }

    private void updateCommentsList(List<Comment> comments) {
        if (mCommentsAdapter == null) {
            mCommentsAdapter = new CommentsAdapter(comments);
            mBinding.setCommentsAdapter(mCommentsAdapter);
            return;
        }
        mCommentsAdapter.setComments(comments);
    }

    @Override
    public void handleError(Throwable throwable) {
        // TODO
    }
}
