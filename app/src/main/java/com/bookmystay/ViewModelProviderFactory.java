package com.bookmystay;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bookmystay.data.datasource.HotelRepository;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final HotelRepository mDataSource;

    @Inject
    ViewModelProviderFactory(HotelRepository dataManager) {
        this.mDataSource = dataManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        try {
            return modelClass
                .getConstructor(HotelRepository.class)
                .newInstance(mDataSource);

        } catch (IllegalAccessException
            | InstantiationException
            | InvocationTargetException
            | NoSuchMethodException e) {

            e.printStackTrace();
        }
        try {
            return modelClass.newInstance();

        } catch (IllegalAccessException
                | InstantiationException e) {

            e.printStackTrace();
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
