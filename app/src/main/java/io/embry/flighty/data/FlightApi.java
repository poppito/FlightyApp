package io.embry.flighty.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface FlightApi {
    @GET("Flight/")
    Call<List<FlightData>> getFlightData(@Query("DepartureAirportCode") String departureAirportCode,
                                         @Query("ArrivalAirportCode") String arrivalAirportCode,
                                         @Query("DepartureDate") String departureDate,
                                         @Query("ArrivalDate") String arrivalDate);

    @GET("Flight/")
    Call<List<FlightData>> getFlightData();
}