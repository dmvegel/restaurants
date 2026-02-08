package com.github.dmvegel.restaurants.common.error;

import static com.github.dmvegel.restaurants.common.error.ErrorType.CONFLICT;

public class VoteTimeExpiredException extends AppException {
    public VoteTimeExpiredException(String msg) {
        super(msg, CONFLICT);
    }
}
