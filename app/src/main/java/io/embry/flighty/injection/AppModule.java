package io.embry.flighty.injection;


import dagger.Module;
import io.embry.flighty.app.FlightyApp;

import javax.inject.Singleton;

@Singleton
@Module
public class AppModule {

    private FlightyApp app;

    public AppModule(FlightyApp app) {
        this.app = app;
    }
}