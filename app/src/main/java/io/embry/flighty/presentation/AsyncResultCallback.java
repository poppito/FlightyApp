package io.embry.flighty.presentation;

public interface AsyncResultCallback<T> {
    void onSuccess(T t);

    void onError(Throwable t);
}