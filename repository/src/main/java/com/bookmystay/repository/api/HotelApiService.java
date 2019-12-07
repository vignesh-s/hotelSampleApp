package com.bookmystay.repository.api;

import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface HotelApiService {
    @GET("api/workshop/hotel/")
    Single<Hotel> doGetHotelDetailsApiCall();

    @GET("api/workshop/comments/")
    Single<List<Comment>> doGetHotelCommentsApiCall();
}
