package com.waes.assignment.exception;

import com.waes.assignment.util.Consts;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Using while not found entity with id given
 *
 * @developer -- ilkercolakoglu
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = Consts.DIFF_NOT_FOUND_EXCEPTION_MESSAGE)
public class DiffNotFoundException extends Exception {

    static final long serialVersionUID = -3387516993224229948L;


    public DiffNotFoundException(String message)
    {
        super(message);
    }
}
