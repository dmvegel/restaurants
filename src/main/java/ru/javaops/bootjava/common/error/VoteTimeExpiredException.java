package ru.javaops.bootjava.common.error;

import static ru.javaops.bootjava.common.error.ErrorType.CONFLICT;

public class VoteTimeExpiredException extends AppException {
    public VoteTimeExpiredException(String msg) {
        super(msg, CONFLICT);
    }
}
