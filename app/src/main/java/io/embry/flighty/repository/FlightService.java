package io.embry.flighty.repository;

import io.embry.flighty.data.FlightData;
import io.embry.flighty.util.AsyncResultCallback;

import java.util.List;

public interface FlightService {
    void retrieveFlightData(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate, AsyncResultCallback<List<FlightData>> callback);

    void retrieveFlightData(AsyncResultCallback<List<FlightData>> callback);
}
