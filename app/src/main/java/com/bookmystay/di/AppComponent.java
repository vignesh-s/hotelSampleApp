package com.bookmystay.di;

import android.app.Application;

import com.bookmystay.BookMyStayApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        ActivityBuilder.class,
        AndroidInjectionModule.class,
        AppModule.class,
        NetworkModule.class
})
public interface AppComponent {

    void inject(BookMyStayApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
