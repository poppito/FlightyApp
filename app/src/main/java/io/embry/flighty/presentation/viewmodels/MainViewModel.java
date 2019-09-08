package io.embry.flighty.presentation.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.embry.flighty.data.FlightData;
import io.embry.flighty.repository.FlightService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainViewModel extends ViewModel {


    private String departureCode;
    private String arrivalCode;
    private Date depatureDate;
    private Date returnDate;

    private FlightService service;

    public void setFlightService(FlightService service) {
        this.service = service;
    }

    private static final DateFormat PRESENTATION_DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private DateFormat repositoryDateFormat =   new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.getDefault()); //don't make this static


    public LiveData<List<FlightData>> getFlightData() {
        LiveData<List<FlightData>> flightData = new MutableLiveData<>();
        flightData = service.retrieveFlightData(departureCode,
                arrivalCode,
                repositoryDateFormat.format(depatureDate),
                repositoryDateFormat.format(returnDate));
        return flightData;
    }


    //region private
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
    //endregion

    //region viewmodel
    public String getInitialDepartureDate() {
        this.depatureDate = getToday();
        return PRESENTATION_DATE_FORMAT.format(depatureDate);
    }

    public String getInitialReturnDate() {
        this.returnDate = getTomorrow();
        return PRESENTATION_DATE_FORMAT.format(returnDate);
    }

    public void setDepartureDate(int year, int month, int dayOfYear) {
        this.depatureDate = transformDate(year, month, dayOfYear);
    }


    public void setReturnDate(int year, int month, int dayOfYear) {
        this.returnDate = transformDate(year, month, dayOfYear);
    }


    public void setDepartureCode(String input) {
        this.departureCode = input;
    }

    public void setArrivalCode(String input) {
        this.arrivalCode = input;
    }

    public String getDepartureCode() {
        return departureCode;
    }

    public String getArrivalCode() {
        return this.arrivalCode;
    }

    public boolean validateReturnDate() {
        return depatureDate.before(returnDate);
    }

    public boolean isQueryDataComplete() {
        return getArrivalCode() != null &&
                !getArrivalCode().isEmpty() &&
                getDepartureCode() != null &&
                !getDepartureCode().isEmpty();
    }
}
