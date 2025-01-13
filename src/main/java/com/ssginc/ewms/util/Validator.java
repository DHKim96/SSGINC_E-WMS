package com.ssginc.ewms.util;

public interface Validator<T> {

    boolean validate(T object);
}
