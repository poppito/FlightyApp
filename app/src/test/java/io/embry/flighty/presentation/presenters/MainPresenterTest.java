package io.embry.flighty.presentation.presenters;

import io.embry.flighty.data.FlightServiceImpl;
import io.embry.flighty.presentation.view.MainActivity;
import io.embry.flighty.repository.FlightService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class MainPresenterTest {


    MainPresenter.ViewSurface surface;
    FlightService service;
    MainPresenterContract presenter;

    @Before
    public void setUp() {
        service = Mockito.mock(FlightServiceImpl.class);
        surface = Mockito.mock(MainActivity.class);
        presenter = new MainPresenter(service);
        presenter.onStart(surface);
    }

    @Test
    public void givenDepartureAirportCodeEntered_butNoReturnAirportCodeEntered_thenViewShowsError() {
        //given
        presenter.handleDepartureAirportInput("ABC");
        presenter.handleReturnDateSet(1, 1, 1);
        presenter.handleDepartureDateSet(1, 1, 1);

        //when
        presenter.handleSubmitClicked();

        //then
        verify(surface).showArrivalAirportError();
    }

    @Test
    public void givenArrivalCodeEntered_butNoDepartureCode_thenViewShowsError() {
        //given
        presenter.handleArrivalAirportInput("CDE");
        presenter.handleReturnDateSet(1, 1, 1);
        presenter.handleDepartureDateSet(1, 1, 1);

        //when
        presenter.handleSubmitClicked();

        //then
        verify(surface).showDepartureAirportError();
    }

    @Test
    public void givenDepartureDate_isLaterThanReturnDate_thenViewShowsError() {
        //given
        presenter.handleArrivalAirportInput("CDE");
        presenter.handleDepartureAirportInput("CDE");
        presenter.handleReturnDateSet(1, 1, 1);
        presenter.handleDepartureDateSet(1, 1, 1);

        //when
        presenter.handleSubmitClicked();

        //then
        verify(surface).showDateError();
    }


    @Test
    public void givenArrivalCodeAndDepartureCodeEntered_whenSearchCalled_thenCallService() {
        presenter.handleArrivalAirportInput("CDE");
        presenter.handleDepartureAirportInput("CDE");
        presenter.handleReturnDateSet(2, 1, 1);
        presenter.handleDepartureDateSet(1, 1, 1);

        //when
        presenter.handleSubmitClicked();

        //then
        verify(service).retrieveFlightData(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any());
    }
}