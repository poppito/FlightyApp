package io.embry.flighty.data;

import com.google.gson.annotations.SerializedName;

public class FlightData {
    @SerializedName("AirlineLogoAddress")
    private String airlineLogoAddress;

    @SerializedName("AirlineName")
    private String AirlineName;


    @SerializedName("InboundFlightsDuration")
    private String inboundFlightsDuration;

    @SerializedName("ItineraryId")
    private String itId;

    @SerializedName("Stops")
    private int stops;

    @SerializedName("OutboundFlightsDuration")
    private String outboundFlightsDuration;

    @SerializedName("TotalAmount")
    private String amount;


    public String getAirlineLogoAddress() {
        return airlineLogoAddress;
    }

    public String getAirlineName() {
        return AirlineName;
    }


    public String getInboundFlightsDuration() {
        return inboundFlightsDuration;
    }

    public String getItId() {
        return itId;
    }

    public int getStops() {
        return stops;
    }

    public String getOutboundFlightsDuration() {
        return outboundFlightsDuration;
    }

    public String getAmount() {
        return amount;
    }
}
