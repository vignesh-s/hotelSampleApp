package com.bookmystay.repository;

import com.bookmystay.data.datasource.HotelRepository;
import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;
import com.bookmystay.repository.local.LocalDataObservable;
import com.bookmystay.repository.local.PreferencesHelper;
import com.bookmystay.repository.api.HotelApiEndPoint;

import java.util.List;

import io.reactivex.Single;

public class HotelRepositoryImpl implements HotelRepository {

    private HotelApiEndPoint mHotelApiEndPoint;
    private PreferencesHelper mPreferencesHelper;
    private NetworkChecker mNetworkChecker;

    public HotelRepositoryImpl(HotelApiEndPoint hotelDataProvider,
                               PreferencesHelper preferencesHelper,
                               NetworkChecker networkChecker) {

        mHotelApiEndPoint = hotelDataProvider;
        mPreferencesHelper = preferencesHelper;
        mNetworkChecker = networkChecker;
    }

    @Override
    public Single<Hotel> getHotelDetails() {
        if (mNetworkChecker.isConnectedToInternet()) {
            return mHotelApiEndPoint.doGetHotelDetailsApiCall();
        }
        return getHotelDetailsFromLocal();
    }

    private Single<Hotel> getHotelDetailsFromLocal() {
        return new LocalDataObservable<Hotel>() {
            @Override
            protected Hotel getData() {
                return mPreferencesHelper.getHotelDetail();
            }
        };
    }

    @Override
    public void saveHotelDetails(final Hotel hotel) {
        mPreferencesHelper.setHotelDetail(hotel);
    }

    public Single<List<Comment>> getHotelComments() {
        if (mNetworkChecker.isConnectedToInternet()) {
            return mHotelApiEndPoint.doGetHotelCommentsApiCall();
        }
        return getCommentsFromLocal();
    }

    private Single<List<Comment>> getCommentsFromLocal() {
        return new LocalDataObservable<List<Comment>>() {
            @Override
            protected List<Comment> getData() {
                return mPreferencesHelper.getComments();
            }
        };
    }

    @Override
    public void saveComments(List<Comment> comments) {
        mPreferencesHelper.setComments(comments);
    }
}
