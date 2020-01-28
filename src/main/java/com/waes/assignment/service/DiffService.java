package com.waes.assignment.service;

import com.waes.assignment.dto.request.DiffRequest;
import com.waes.assignment.dto.response.DiffResponse;
import com.waes.assignment.exception.BothSideNotFoundException;
import com.waes.assignment.exception.DiffNotFoundException;
import com.waes.assignment.exception.InvalidSideException;

/**
 * Provides logic methods for diff entity and API
 *
 * @developer -- ilkercolakoglu
 */
public interface DiffService {

    boolean save(DiffRequest diffRequest, Long id, String side) throws InvalidSideException;

    DiffResponse getDifference(long id) throws BothSideNotFoundException, DiffNotFoundException;

}
