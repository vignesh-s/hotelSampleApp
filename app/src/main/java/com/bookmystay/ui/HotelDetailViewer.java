package com.bookmystay.ui;

import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;

import java.util.List;

public interface HotelDetailViewer {

    void onHotelDetailLoaded(Hotel hotel);

    void handleError(Throwable throwable);

    void onCommentsLoaded(List<Comment> comments);
}
