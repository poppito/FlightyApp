package io.embry.flighty.presentation.presenters;

public interface MainPresenterContract<V>{
    void onStart(V v);
    void handleDepartureDateClick();
    void handleReturnDateClick();
    void handleDepartureDateSet(int year, int month, int dayOfYear);
    void handleReturnDateSet(int year, int month, int dayOfYear);
    void handleSubmitClicked();
    void handleDepartureAirportInput(String input);
    void handleArrivalAirportInput(String input);
}
