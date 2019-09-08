package io.embry.flighty.injection;


import android.app.Activity;
import dagger.Module;
import dagger.Provides;
import io.embry.flighty.app.FlightyApp;
import retrofit2.Retrofit;

@Module
public class ActivityModule {

    private Retrofit retrofit;

    public ActivityModule(Activity activity) {
        FlightyApp app = (FlightyApp) activity.getApplication();
        retrofit = app.retrofit;
    }

    @Provides
    Retrofit providesRetrofit() {
        return retrofit;
    }
}
