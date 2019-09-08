package io.embry.flighty.repository;

import androidx.lifecycle.LiveData;
import io.embry.flighty.data.FlightData;
import io.embry.flighty.util.AsyncResultCallback;

import java.util.List;

public interface FlightService {
    LiveData<List<FlightData>> retrieveFlightData(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate);

    void retrieveFlightData(AsyncResultCallback<List<FlightData>> callback);
}
