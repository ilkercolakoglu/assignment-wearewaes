package com.waes.assignment.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic Api Error.
 * Using while handling exception
 *
 * @developer -- ilkercolakoglu
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ApiError {

    private String cause;
    private String uri;
    private String timestamp;
    private int status;

}
