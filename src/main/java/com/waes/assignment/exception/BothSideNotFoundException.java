package com.waes.assignment.exception;

import com.waes.assignment.util.Consts;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Using while one of sides is empty or null
 *
 * @developer -- ilkercolakoglu
 */
@ResponseStatus(value = HttpStatus.PRECONDITION_REQUIRED, reason = Consts.BOTH_SIDE_NOT_FOUND_EXCEPTION_MESSAGE)
public class BothSideNotFoundException extends Exception {

    static final long serialVersionUID = -3387516993224229948L;


    public BothSideNotFoundException(String message)
    {
        super(message);
    }
}
