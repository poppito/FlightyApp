package io.embry.flighty.data;

import androidx.annotation.Nullable;
import io.embry.flighty.util.AsyncResultCallback;
import io.embry.flighty.repository.FlightService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.inject.Inject;
import java.util.List;

public class FlightServiceImpl implements FlightService {

    private FlightApi service;

    @Inject
    public FlightServiceImpl(Retrofit retrofit) {
        service = retrofit.create(FlightApi.class);
    }

    public void retrieveFlightData(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate, AsyncResultCallback<List<FlightData>> callback) {
        service.getFlightData(departureAirportCode, arrivalAirportCode, departureDate, arrivalDate).enqueue(new Callback<List<FlightData>>() {
            @Override
            public void onResponse(@Nullable Call<List<FlightData>> call, @Nullable Response<List<FlightData>> response) {
                if (response != null && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<List<FlightData>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void retrieveFlightData(AsyncResultCallback<List<FlightData>> callback) {
        service.getFlightData().enqueue(new Callback<List<FlightData>>() {
            @Override
            public void onResponse(Call<List<FlightData>> call, Response<List<FlightData>> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<FlightData>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
