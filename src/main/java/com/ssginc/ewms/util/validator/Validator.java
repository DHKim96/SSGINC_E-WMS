package com.ssginc.ewms.util.validator;

public interface Validator<T> {

    boolean validate(T object);
}
