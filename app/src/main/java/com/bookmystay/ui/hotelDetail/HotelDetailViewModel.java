package com.bookmystay.ui.hotelDetail;

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
        getCompositeDisposable().add(mHotelRepository.getHotelDetails()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(hotel -> mHotelRepository.saveHotelDetails(hotel))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hotel -> getViewer().onHotelDetailLoaded(hotel),
                        throwable -> getViewer().handleError(throwable)
                ));
    }

    void loadComments() {
        getCompositeDisposable().add(mHotelRepository.getHotelComments()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(comments -> mHotelRepository.saveComments(comments))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        comments -> {
                            getViewer().onCommentsLoaded(comments);
                        },
                        throwable -> getViewer().handleError(throwable)
                ));
    }

}
