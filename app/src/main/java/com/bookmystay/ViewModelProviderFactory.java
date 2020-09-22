package com.bookmystay;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bookmystay.data.datasource.HotelRepository;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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
            boolean isDataSourceNeeded = false;
            try {
                modelClass.getConstructor(HotelRepository.class);
                isDataSourceNeeded = true;
            } catch (NoSuchMethodException ignored) {
            }
            if (isDataSourceNeeded) {
                return modelClass
                        .getConstructor(HotelRepository.class)
                        .newInstance(mDataSource);
            } else {
                return modelClass.newInstance();
            }
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
