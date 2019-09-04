package io.embry.flighty.injection;


import android.app.Activity;
import dagger.Module;
import dagger.Provides;
import io.embry.flighty.app.FlightyApp;
import io.embry.flighty.data.FlightServiceImpl;
import io.embry.flighty.presentation.presenters.MainPresenter;
import io.embry.flighty.presentation.presenters.MainPresenterContract;
import io.embry.flighty.repository.FlightService;
import retrofit2.Retrofit;

@Module
public class ActivityModule {

    private Retrofit retrofit;

    public ActivityModule(Activity activity) {
        FlightyApp app = (FlightyApp) activity.getApplication();
        retrofit = app.retrofit;
    }

    @Provides
    MainPresenterContract provideMainPresenter(FlightService service) {
        return new MainPresenter(service);
    }

    @Provides
    Retrofit providesRetrofit() {
        return retrofit;
    }

    @Provides
    FlightService providesFlightService(Retrofit retrofit) {
        return new FlightServiceImpl(retrofit);
    }
}
