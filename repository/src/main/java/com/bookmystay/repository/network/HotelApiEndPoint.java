package com.bookmystay.repository.network;

import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface HotelApiEndPoint {
    @GET("api/workshop/hotel/")
    Single<Hotel> doGetHotelDetailsApiCall();

    @GET("api/workshop/comments/")
    Single<List<Comment>> doGetHotelCommentsApiCall();
}
