package com.bookmystay.repository;

import androidx.annotation.VisibleForTesting;

import com.bookmystay.data.datasource.HotelRepository;
import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;
import com.bookmystay.repository.api.HotelApiService;
import com.bookmystay.repository.local.LocalDataObservable;
import com.bookmystay.repository.local.PreferencesHelper;

import java.util.List;

import io.reactivex.Single;

public class HotelRepositoryImpl implements HotelRepository {

    private HotelApiService mHotelApiService;
    private PreferencesHelper mPreferencesHelper;
    private NetworkChecker mNetworkChecker;

    public HotelRepositoryImpl(HotelApiService hotelApiService,
                               PreferencesHelper preferencesHelper,
                               NetworkChecker networkChecker) {

        mHotelApiService = hotelApiService;
        mPreferencesHelper = preferencesHelper;
        mNetworkChecker = networkChecker;
    }

    @Override
    public Single<Hotel> getHotelDetails() {
        if (mNetworkChecker.isConnectedToInternet()) {
            return mHotelApiService.doGetHotelDetailsApiCall();
        }
        return getHotelDetailsFromLocal();
    }

    @VisibleForTesting
    Single<Hotel> getHotelDetailsFromLocal() {
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
            return mHotelApiService.doGetHotelCommentsApiCall();
        }
        return getCommentsFromLocal();
    }

    @VisibleForTesting
    Single<List<Comment>> getCommentsFromLocal() {
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
