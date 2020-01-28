package com.waes.assignment.exception;

import com.waes.assignment.util.Consts;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Using while side given is invalid
 *
 * @developer -- ilkercolakoglu
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = Consts.INVALID_SIDE_EXCEPTION_MESSAGE)
public class InvalidSideException extends Exception {

    static final long serialVersionUID = -3387516993224229948L;


    public InvalidSideException(String message)
    {
        super(message);
    }
}
