package com.bookmystay.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bookmystay.R;
import com.bookmystay.data.model.Comment;
import com.bookmystay.databinding.CommentsListItemBinding;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Comment> mComments;

    CommentsAdapter(List<Comment> products) {
        this.mComments = products;
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.comments_list_item, parent, false);

         CommentsListItemBinding binding = DataBindingUtil.bind(view);
        assert binding != null;
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CommentViewHolder commentViewHolder = (CommentViewHolder) viewHolder;
        final Comment product = mComments.get(position);
        commentViewHolder.bind(product);
    }

    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        CommentsListItemBinding binding;

        CommentViewHolder(CommentsListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Object product) {
            binding.setVariable(com.bookmystay.BR.comment, product);
            binding.executePendingBindings();
        }
    }
}
