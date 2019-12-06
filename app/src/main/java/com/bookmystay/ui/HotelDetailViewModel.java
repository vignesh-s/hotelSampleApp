package com.bookmystay.ui;

import com.bookmystay.data.datasource.HotelRepository;
import com.bookmystay.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HotelDetailViewModel extends BaseViewModel<HotelDetailViewer> {

    private HotelRepository mHotelRepository;

    public HotelDetailViewModel(HotelRepository hotelRepository) {
        mHotelRepository = hotelRepository;
    }

    void loadHotelDetails() {
        getCompositeDisposable().add(mHotelRepository.doGetHotelDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hotel -> {
                            getViewer().onHotelDetailLoaded(hotel);
                            mHotelRepository.saveHotelDetails(hotel);
                        },
                        throwable -> getViewer().handleError(throwable)
                ));
    }

    void loadComments() {
        getCompositeDisposable().add(mHotelRepository.doGetHotelCommentsApiCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        comments -> getViewer().onCommentsLoaded(comments),
                        throwable -> getViewer().handleError(throwable)
                ));
    }
}
