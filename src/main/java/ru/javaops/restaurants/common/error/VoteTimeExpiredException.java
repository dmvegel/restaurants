package ru.javaops.restaurants.common.error;

import static ru.javaops.restaurants.common.error.ErrorType.CONFLICT;

public class VoteTimeExpiredException extends AppException {
    public VoteTimeExpiredException(String msg) {
        super(msg, CONFLICT);
    }
}
