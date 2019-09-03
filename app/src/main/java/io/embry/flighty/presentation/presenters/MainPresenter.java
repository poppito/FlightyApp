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

public class MainPresenter extends BasePresenter<MainPresenter.ViewSurface> {

    private FlightService service;
    private ViewSurface viewSurface;

    @Inject
    public MainPresenter(FlightService service) {
        this.service = service;
    }

    //region base presenter
    @Override
    public void onStart(ViewSurface viewSurface) {
        this.viewSurface = viewSurface;
        //setDepartureDate();
        //setReturnDate();
        service.retrieveFlightData(new AsyncResultCallback<List<FlightData>>() {
            @Override
            public void onSuccess(List<FlightData> flightData) {
                if (flightData != null && !flightData.isEmpty()) {
                    for (FlightData data : flightData) {
                        Log.v("TAG", data.getAirlineLogoAddress());
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onStop(ViewSurface viewSurface) {

    }

    @Override
    protected void onResume() {
    }
    //endregion

    //region private
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
    //endregion

    public interface ViewSurface {
    }
}
