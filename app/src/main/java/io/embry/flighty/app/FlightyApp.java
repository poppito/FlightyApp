package io.embry.flighty.app;

import android.app.Application;
import io.embry.flighty.BuildConfig;
import io.embry.flighty.data.FlightServiceImpl;
import io.embry.flighty.injection.AppComponent;
import io.embry.flighty.injection.AppModule;
import io.embry.flighty.injection.DaggerAppComponent;
import io.embry.flighty.repository.FlightService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class FlightyApp extends Application {

    public Retrofit retrofit;
    public AppComponent appComponent;
    public FlightService service;

    @Override
    public void onCreate() {
        super.onCreate();
        buildHttpServices();
        inject();
    }


    private void buildHttpServices() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        service = new FlightServiceImpl(retrofit);
    }

    private void inject() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}