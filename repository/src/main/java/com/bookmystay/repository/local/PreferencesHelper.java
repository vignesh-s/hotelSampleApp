package com.bookmystay.repository.local;

import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;

import java.util.List;

public interface PreferencesHelper {

    Hotel getHotelDetail();

    void setHotelDetail(Hotel hotelDetail);

    List<Comment> getComments();

    void setComments(List<Comment> comments);

}
