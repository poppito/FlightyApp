package io.embry.flighty.util;

public interface AsyncResultCallback<T> {
    void onSuccess(T t);

    void onError(Throwable t);
}