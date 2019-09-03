package io.embry.flighty.injection;


import dagger.Component;
import io.embry.flighty.app.FlightyApp;

import javax.inject.Singleton;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(FlightyApp app);
}
