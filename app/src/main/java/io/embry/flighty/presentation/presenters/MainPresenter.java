package io.embry.flighty.presentation.presenters;

import android.util.Log;
import io.embry.flighty.data.FlightData;
import io.embry.flighty.repository.FlightService;
import io.embry.flighty.util.AsyncResultCallback;

import javax.inject.Inject;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainPresenter implements MainPresenterContract<MainPresenter.ViewSurface> {

    private FlightService service;
    private ViewSurface viewSurface;

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);

    private String departureAirportCode = null;
    private String arrivalAirportCode = null;
    private Date departureDate = null;
    private Date returnDate = null;

    @Inject
    public MainPresenter(FlightService service) {
        this.service = service;
    }


    //region MainPresenterContract
    public void onStart(ViewSurface viewSurface) {
        this.viewSurface = viewSurface;
        this.departureDate = getToday();
        this.returnDate = getTomorrow();
        renderInitialDateValues();
    }

    public void handleDepartureDateClick() {
        viewSurface.handleDepartureDateChoices();
    }

    public void handleReturnDateClick() {
        viewSurface.handleReturnDateChoices();
    }

    @Override
    public void handleDepartureDateSet(int year, int month, int dayOfYear) {
        Date date = transformDate(year, month, dayOfYear);
        this.departureDate = date;
        viewSurface.setDepartureDate(DATE_FORMAT.format(date));
    }

    @Override
    public void handleReturnDateSet(int year, int month, int dayOfYear) {
        Date date = transformDate(year, month, dayOfYear);
        this.returnDate = date;
        viewSurface.setReturnDate(DATE_FORMAT.format(date));
    }

    @Override
    public void handleDepartureAirportInput(String input) {
        this.departureAirportCode = input;
    }

    @Override
    public void handleArrivalAirportInput(String input) {
        this.arrivalAirportCode = input;
    }

    @Override
    public void handleSubmitClicked() {
        if (isQueryDataComplete()) {
            if (validateReturnDate()) {
                retrieveFlightData();
            } else {
                viewSurface.showDateError();
            }
        } else {
            if (arrivalAirportCode == null || arrivalAirportCode.equalsIgnoreCase("")) {
                viewSurface.showArrivalAirportError();
            } else if (departureAirportCode == null || departureAirportCode.equalsIgnoreCase("")) {
                viewSurface.showDepartureAirportError();
            }
        }
    }

    //endregion

    //region private
    public void retrieveFlightData() {
        viewSurface.showLoader();
        service.retrieveFlightData(new AsyncResultCallback<List<FlightData>>() {
            @Override
            public void onSuccess(List<FlightData> flightData) {
                viewSurface.hideLoader();
                if (flightData != null && !flightData.isEmpty()) {
                    for (FlightData data : flightData) {
                        Log.v("TAG", data.getAirlineLogoAddress());
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                viewSurface.hideLoader();
            }
        });
    }

    private void renderInitialDateValues() {
        viewSurface.setReturnDate(DATE_FORMAT.format(returnDate));
        viewSurface.setDepartureDate(DATE_FORMAT.format(departureDate));
    }

    private Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
        return calendar.getTime();
    }

    private Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    private Date transformDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
        return calendar.getTime();
    }

    private boolean isQueryDataComplete() {
        return arrivalAirportCode != null &&
                departureAirportCode != null &&
                !arrivalAirportCode.equalsIgnoreCase("") &&
                !departureAirportCode.equalsIgnoreCase("") &&
                departureDate != null &&
                returnDate != null;
    }

    private boolean validateReturnDate() {
        return departureDate.before(returnDate);
    }
    //endregion

    public interface ViewSurface {
        void showLoader();

        void setDepartureDate(String departureDate);

        void setReturnDate(String returnDate);

        void handleDepartureDateChoices();

        void handleReturnDateChoices();

        void showDateError();

        void hideLoader();

        void showDepartureAirportError();

        void showArrivalAirportError();
    }
}
