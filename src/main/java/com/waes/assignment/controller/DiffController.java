package com.waes.assignment.controller;

import com.waes.assignment.dto.request.DiffRequest;
import com.waes.assignment.dto.response.DiffResponse;
import com.waes.assignment.exception.BothSideNotFoundException;
import com.waes.assignment.exception.DiffNotFoundException;
import com.waes.assignment.exception.InvalidSideException;
import com.waes.assignment.service.DiffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.waes.assignment.util.Consts.*;

/**
 * Provides create diff with base64 encoded json and get differences between two json
 *
 * @developer -- ilkercolakoglu
 */
@Api(value = "Finds the difference between two Base64 encoded JSON")
@RestController
@RequestMapping("/v1/diff")
public class DiffController {

    private DiffService diffService;

    public DiffController(DiffService diffService) {
        this.diffService = diffService;
    }

    /**
     * Saves base64 encode JSON to the side
     *
     * @param diffRequest diff request includes encodedJSONValue
     * @param id          diff id
     */
    @ApiOperation(value = "Provides saving Base64 encode JSON value to the side given")
    @PostMapping("/{id}/{side}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 422, message = INVALID_SIDE_EXCEPTION_MESSAGE),
            @ApiResponse(code = 400, message = BAD_REQUEST_EXCEPTION_MESSAGE)
    })
    public ResponseEntity<?> create(@RequestBody @Valid final DiffRequest diffRequest,
                                    @PathVariable("id") final Long id,
                                    @PathVariable("side") final String side) throws InvalidSideException {
        URI location = URI.create(String.format("/v1/diff/%s/%s", id, side));
        return ResponseEntity.created(location).body(diffService.save(diffRequest, id, side));
    }

    /**
     * Endpoint that returns the difference of the two sides
     *
     * @param id the diff identity
     * @return the difference info
     */
    @ApiOperation(value = "Provides the difference between two Base64 encoded JSON")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = DIFF_NOT_FOUND_EXCEPTION_MESSAGE),
            @ApiResponse(code = 428, message = BOTH_SIDE_NOT_FOUND_EXCEPTION_MESSAGE)
    })
    public DiffResponse getDiff(@PathVariable("id") final Long id) throws BothSideNotFoundException,
            DiffNotFoundException {
        return diffService.getDifference(id);
    }
}

