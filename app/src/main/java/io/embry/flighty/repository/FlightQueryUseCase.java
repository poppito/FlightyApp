package io.embry.flighty.repository;

import io.embry.flighty.data.FlightData;
import io.embry.flighty.data.FlightQueryData;

public interface FlightQueryUseCase {
    FlightData retrieveFlightData(FlightQueryData data);
}
