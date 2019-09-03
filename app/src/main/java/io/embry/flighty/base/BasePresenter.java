package io.embry.flighty.base;

public interface BasePresenter<V> {
    void onStart();
    void onPause();
    void onStop();
    void onResume();
}
