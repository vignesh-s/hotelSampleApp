package com.bookmystay.di;

import com.bookmystay.ui.HotelDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract HotelDetailActivity bindHotelDetailActivity();

}
