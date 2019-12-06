package com.bookmystay.data.datasource;

import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;

import java.util.List;

import io.reactivex.Single;

public interface HotelRepository {

    Single<Hotel> getHotelDetails();

    void saveHotelDetails(Hotel hotel);

    Single<List<Comment>> getHotelComments();

    void saveComments(List<Comment> hotel);
}
