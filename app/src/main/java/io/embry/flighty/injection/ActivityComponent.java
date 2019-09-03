package io.embry.flighty.injection;


import dagger.Component;
import io.embry.flighty.presentation.MainActivity;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);
}
