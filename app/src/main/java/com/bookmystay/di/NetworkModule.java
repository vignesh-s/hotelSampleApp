package com.bookmystay.di;

import android.content.Context;

import com.bookmystay.data.datasource.HotelRepository;
import com.bookmystay.repository.NetworkChecker;
import com.bookmystay.repository.HotelRepositoryImpl;
import com.bookmystay.repository.local.PreferencesHelperImpl;
import com.bookmystay.repository.local.PreferencesHelper;
import com.bookmystay.repository.api.HotelApiEndPoint;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://hiringworkshop.herokuapp.com/";

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES);

        // Set log level
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(httpLoggingInterceptor);

        return httpClient.build();
    }

    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(
            OkHttpClient client,
            GsonConverterFactory converterFactory,
            RxJava2CallAdapterFactory adapterFactory) {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    HotelApiEndPoint provideHotelApiEndPoint(Retrofit retrofit) {
        return retrofit.create(HotelApiEndPoint.class);
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(Context context, Gson gson) {
        return new PreferencesHelperImpl(context, gson);
    }

    @Provides
    @Singleton
    HotelRepository provideHotelDataSource(HotelApiEndPoint hotelApiEndPoint,
                                           PreferencesHelper preferencesHelper,
                                           NetworkChecker networkChecker) {

        return new HotelRepositoryImpl(hotelApiEndPoint, preferencesHelper, networkChecker);
    }
}
