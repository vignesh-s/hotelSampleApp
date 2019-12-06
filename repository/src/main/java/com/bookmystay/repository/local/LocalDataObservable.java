package com.bookmystay.repository.local;

import com.bookmystay.repository.local.LocalDataException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;

public abstract class LocalDataObservable<T> extends Single<T> {

    protected abstract T getData();

    @Override
    protected void subscribeActual(SingleObserver<? super T> observer) {
        T hotel = getData();
        if (hotel != null) {
            observer.onSuccess(hotel);
        } else {
            observer.onError(new LocalDataException());
        }
    }
}
