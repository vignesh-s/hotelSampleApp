package com.bookmystay.repository.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class PreferencesHelperImpl implements PreferencesHelper {

    private static final String PREF_NAME = "PREF_NAME_BOOK_MY_STAY";

    private static final String PREF_KEY_HOTEL_DETAIL = "PREF_KEY_HOTEL_DETAIL";

    private static final String PREF_KEY_COMMENTS = "PREF_KEY_COMMENTS";

    private final SharedPreferences mPrefs;
    private final Gson mGson;

    public PreferencesHelperImpl(Context context, Gson gson) {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mGson = gson;
    }

    @Override
    public Hotel getHotelDetail() {
        return mGson.fromJson(mPrefs.getString(PREF_KEY_HOTEL_DETAIL, null), Hotel.class);
    }

    @Override
    public void setHotelDetail(Hotel hotel) {
        mPrefs.edit().putString(PREF_KEY_HOTEL_DETAIL, mGson.toJson(hotel)).apply();
    }

    @Override
    public List<Comment> getComments() {
        String commentsString = mPrefs.getString(PREF_KEY_COMMENTS, null);
        if (TextUtils.isEmpty(commentsString)) {
            return null;
        }
        return mGson.fromJson(commentsString, new TypeToken<List<Comment>>(){}.getType());
    }

    @Override
    public void setComments(List<Comment> comments) {
        String commentsString = mGson.toJson(comments);
        mPrefs.edit().putString(PREF_KEY_COMMENTS, commentsString).apply();
    }


}
