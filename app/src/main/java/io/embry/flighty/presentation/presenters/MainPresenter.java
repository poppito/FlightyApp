package io.embry.flighty.presentation.presenters;

import android.util.Log;
import io.embry.flighty.base.BasePresenter;
import io.embry.flighty.data.FlightData;
import io.embry.flighty.presentation.AsyncResultCallback;
import io.embry.flighty.repository.FlightService;

import javax.inject.Inject;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainPresenter implements BasePresenter {

    private FlightService service;

    @Inject
    public MainPresenter(FlightService service) {
        this.service = service;
    }

    private void setDepartureDate() {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //departureDate.setText(format.format(getToday()));
    }

    private void setReturnDate() {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //returnDate.setText(format.format(getTomorrow()));
    }

    private Date getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        return calendar.getTime();
    }

    @Override
    public void onStart() {
        service.retrieveFlightData(new AsyncResultCallback<List<FlightData>>() {
            @Override
            public void onSuccess(List<FlightData> flightData) {
                Log.v("TAG", "success");
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    @Override
    public void onPause() {
        service = null;
    }

    @Override
    public void onStop() {
        service = null;
    }

    @Override
    public void onResume() {

    }
}
