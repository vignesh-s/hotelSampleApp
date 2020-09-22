package com.bookmystay.ui;

import androidx.lifecycle.MutableLiveData;

import com.bookmystay.data.model.Comment;
import com.bookmystay.ui.base.BaseViewModel;
import com.bookmystay.ui.hotelDetail.HotelDetailViewer;

import java.util.ArrayList;


public class HomeSharedViewModel extends BaseViewModel<HotelDetailViewer> {

    public MutableLiveData<ArrayList<Comment>> mComments = new MutableLiveData<>();

}
