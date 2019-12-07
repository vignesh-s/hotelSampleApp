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

import java.util.ArrayList;

import javax.inject.Inject;

public class PostCommentFragment
        extends BaseFragment<FragmentPostCommentBinding, PostCommentViewModel>
        implements PostCommentViewer {

    @Inject
    ViewModelProviderFactory factory;
    private PostCommentViewModel mPostCommentViewModel;
    private HomeSharedViewModel mHomeSharedViewModel;
    private FragmentPostCommentBinding mBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_post_comment;
    }

    @Override
    public PostCommentViewModel getViewModel() {
        mPostCommentViewModel =
                ViewModelProviders.of(this, factory).get(PostCommentViewModel.class);

        return mPostCommentViewModel;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() == null) {
            return;
        }
        mHomeSharedViewModel = ((HotelDetailActivity) getActivity()).mSharedViewModel;
        mBinding.submitButton.setOnClickListener(v -> submitComment());
    }

    private void submitComment() {
        String name = getStringFromEditText(mBinding.etName);
        String comment = getStringFromEditText(mBinding.etComment);
        addComment(new Comment(name, comment));
    }

    private void addComment(Comment comment) {
        ArrayList<Comment> comments = mHomeSharedViewModel.mComments.getValue();
        if (comments == null) {
            return;
        }
        comments.add(comment);
        mHomeSharedViewModel.mComments.postValue(comments);
    }

    private String getStringFromEditText(AppCompatEditText appCompatEditText) {
        Editable editable = appCompatEditText.getText();
        if (editable == null) {
            return null;
        }
        return editable.toString();
    }
}
