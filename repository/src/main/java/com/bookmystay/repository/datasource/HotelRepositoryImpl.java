package com.bookmystay.repository.datasource;

import com.bookmystay.data.datasource.HotelRepository;
import com.bookmystay.data.model.Comment;
import com.bookmystay.data.model.Hotel;
import com.bookmystay.repository.NetworkChecker;
import com.bookmystay.repository.local.PreferencesHelper;
import com.bookmystay.repository.network.HotelApiEndPoint;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;

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

    private Single<Hotel> doGetHotelDetailsFromLocal() {
        return new Single<Hotel>() {
            @Override
            protected void subscribeActual(SingleObserver<? super Hotel> observer) {
                observer.onSuccess(mPreferencesHelper.getHotelDetail());
            }
        };
    }

    @Override
    public Single saveHotelDetails(final Hotel hotel) {
        return new Single() {
            @Override
            protected void subscribeActual(SingleObserver observer) {
                mPreferencesHelper.setHotelDetail(hotel);
            }
        };
    }

    public Single<Hotel> doGetHotelDetails() {
        if (mNetworkChecker.isConnectedToInternet()) {
            return mHotelApiEndPoint.doGetHotelDetailsApiCall();
        }
        return doGetHotelDetailsFromLocal();
    }

    public Single<List<Comment>> doGetHotelCommentsApiCall() {
        return mHotelApiEndPoint.doGetHotelCommentsApiCall();
    }

}
