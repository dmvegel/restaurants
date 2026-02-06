package com.github.dmvegel.restaurants.common.error;

import static com.github.dmvegel.restaurants.common.error.ErrorType.BAD_REQUEST;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg, BAD_REQUEST);
    }
}