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

    public void setAirlineLogoAddress(String airlineLogoAddress) {
        this.airlineLogoAddress = airlineLogoAddress;
    }

    public String getAirlineName() {
        return AirlineName;
    }

    public void setAirlineName(String airlineName) {
        AirlineName = airlineName;
    }

    public String getInboundFlightsDuration() {
        return inboundFlightsDuration;
    }

    public void setInboundFlightsDuration(String inboundFlightsDuration) {
        this.inboundFlightsDuration = inboundFlightsDuration;
    }

    public String getItId() {
        return itId;
    }

    public void setItId(String itId) {
        this.itId = itId;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public String getOutboundFlightsDuration() {
        return outboundFlightsDuration;
    }

    public void setOutboundFlightsDuration(String outboundFlightsDuration) {
        this.outboundFlightsDuration = outboundFlightsDuration;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
