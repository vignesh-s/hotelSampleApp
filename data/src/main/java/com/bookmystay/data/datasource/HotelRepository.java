package com.bookmystay.data.datasource;

import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;

import java.util.List;

import io.reactivex.Single;

public interface HotelRepository {

    Single saveHotelDetails(Hotel hotel);

    Single<Hotel> doGetHotelDetails();

    Single<List<Comment>> doGetHotelCommentsApiCall();

}
