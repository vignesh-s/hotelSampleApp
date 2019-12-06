package com.bookmystay.di;

import android.app.Application;
import android.content.Context;


import com.bookmystay.repository.NetworkChecker;
import com.bookmystay.util.NetworkCheckerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    NetworkChecker provideNetworkChecker(Context context) {
        return new NetworkCheckerImpl(context);
    }
}
