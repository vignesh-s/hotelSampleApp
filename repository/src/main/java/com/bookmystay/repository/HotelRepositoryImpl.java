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
            // Uncomment this if api call is not working
//            return new Single<Hotel>() {
//                @Override
//                protected void subscribeActual(@NonNull SingleObserver<? super Hotel> observer) {
//                    Hotel hotel = new Hotel();
//                    hotel.setName("ITC Grand Chola");
//                    hotel.setCost("50000");
//                    hotel.setDescription("Some description");
//                    hotel.setId(1L);
//                    hotel.setLocation("Chennai");
//                    hotel.setNumberOfReviews("50");
//                    hotel.setRating("4.1");
//                    observer.onSuccess(hotel);
//                }
//            };
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
            // Uncomment this if api call is not working
//            return new Single<List<Comment>>() {
//                @Override
//                protected void subscribeActual(@NonNull SingleObserver<? super List<Comment>> observer) {
//                    List<Comment> comments = new ArrayList<>();
//                    Comment comment = new Comment("Hi", "Vignesh");
//                    comments.add(comment);
//                    observer.onSuccess(comments);
//                }
//            };
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
