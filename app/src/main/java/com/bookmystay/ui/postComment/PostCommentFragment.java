package com.bookmystay.ui.postComment;

import android.os.Bundle;
import android.text.Editable;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import com.bookmystay.BR;
import com.bookmystay.R;
import com.bookmystay.ViewModelProviderFactory;
import com.bookmystay.data.model.Comment;
import com.bookmystay.databinding.FragmentPostCommentBinding;
import com.bookmystay.ui.HomeSharedViewModel;
import com.bookmystay.ui.HotelDetailActivity;
import com.bookmystay.ui.base.BaseFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import javax.inject.Inject;

public class PostCommentFragment
        extends BaseFragment<FragmentPostCommentBinding, HomeSharedViewModel>
        implements PostCommentViewer {

    @Inject
    ViewModelProviderFactory factory;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_post_comment;
    }

    @Override
    public HomeSharedViewModel getViewModel() {
        mViewModel =
                ViewModelProviders.of(getActivity(), factory).get(HomeSharedViewModel.class);

        return mViewModel;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() == null) {
            return;
        }
        mBinding.submitButton.setOnClickListener(v -> submitComment());
    }

    private void submitComment() {
        String name = getStringFromEditText(mBinding.etName);
        String comment = getStringFromEditText(mBinding.etComment);
        addComment(new Comment(name, comment));
    }

    private void addComment(Comment comment) {
        ArrayList<Comment> comments = mViewModel.mComments.getValue();
        if (comments == null) {
            Snackbar.make(mRootView, "comments == null", Snackbar.LENGTH_SHORT).show();
            return;
        }
        comments.add(comment);
        mViewModel.mComments.postValue(comments);
        Snackbar.make(mRootView, "posted", Snackbar.LENGTH_SHORT).show();
    }

    private String getStringFromEditText(AppCompatEditText appCompatEditText) {
        Editable editable = appCompatEditText.getText();
        if (editable == null) {
            return null;
        }
        return editable.toString();
    }
}
