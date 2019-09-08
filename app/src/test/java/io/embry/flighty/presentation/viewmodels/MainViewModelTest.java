package io.embry.flighty.presentation.viewmodels;

import android.app.Activity;
import io.embry.flighty.data.FlightServiceImpl;
import io.embry.flighty.presentation.view.MainActivity;
import io.embry.flighty.repository.FlightService;
import org.junit.Before;
import org.mockito.Mockito;

public class MainViewModelTest {

    FlightService service;
    Activity main;


    @Before
    public void setUp() {
        service = Mockito.mock(FlightServiceImpl.class);
        main = Mockito.mock(MainActivity.class);
    }
}