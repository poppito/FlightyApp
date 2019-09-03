package io.embry.flighty.base;

public abstract class BasePresenter<V> {
    public abstract void onStart(V v);

    protected abstract void onPause();

    protected abstract void onStop(V v);

    protected abstract void onResume();
}
