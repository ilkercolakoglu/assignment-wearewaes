package com.waes.assignment.service.impl;

import com.waes.assignment.dto.request.DiffRequest;
import com.waes.assignment.dto.response.DiffResponse;
import com.waes.assignment.entity.Diff;
import com.waes.assignment.enums.ContentStatus;
import com.waes.assignment.enums.Side;
import com.waes.assignment.exception.BothSideNotFoundException;
import com.waes.assignment.exception.DiffNotFoundException;
import com.waes.assignment.exception.InvalidSideException;
import com.waes.assignment.repository.DiffRepository;
import com.waes.assignment.service.DiffService;
import org.springframework.stereotype.Service;

import java.util.Base64;

import static com.waes.assignment.util.Consts.*;

/**
 * Implements diffService interface and logic methods for diff entity and API
 * Saving diff using repository and finding difference between two json
 *
 * @developer -- ilkercolakoglu
 */
@Service
public class DiffServiceImpl implements DiffService {

    private final DiffRepository diffRepository;

    public DiffServiceImpl(DiffRepository diffRepository) {
        this.diffRepository = diffRepository;
    }


    @Override
    public boolean save(DiffRequest diffRequest, Long id, String side) throws InvalidSideException {

        // retrieve or create a new diff object
        Diff diff = diffRepository.findById(id)
                .orElse(new Diff(id));

        // find correct side and set base64 encoded JSON
        if (Side.LEFT.getValue().equals(side)) {
            diff.setLeftSide(diffRequest.getBase64encodedJSON());
        } else if (Side.RIGHT.getValue().equals(side)) {
            diff.setRightSide(diffRequest.getBase64encodedJSON());
        }
        else{
            throw new InvalidSideException(INVALID_SIDE_EXCEPTION_MESSAGE);
        }

        diffRepository.save(diff);

        return true;
    }

    @Override
    public DiffResponse getDifference(long id) throws BothSideNotFoundException, DiffNotFoundException {
        Diff diff = diffRepository.findById(id)
                .orElseThrow(() -> new DiffNotFoundException(DIFF_NOT_FOUND_EXCEPTION_MESSAGE));

        if (!validateBothSide(diff)) {
            throw new BothSideNotFoundException(BOTH_SIDE_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        ContentStatus contentStatus = findContentStatus(diff);

        // set offsets and content length only if contents are different and the same size. Or just return with content status
        if (ContentStatus.SAME_SIZE_DIFFERENT_CONTENT.equals(contentStatus)) {
            return DiffResponse.builder()
                    .status(contentStatus)
                    .offsets(findDifferenceBetweenLeftAndRight(diff))
                    .length(retrieveJsonStringFromBase64Encoded(diff.getLeftSide()).length())
                    .build();
        } else {
            return DiffResponse.builder()
                    .status(contentStatus)
                    .build();
        }
    }

    private boolean validateBothSide(Diff diff) {
        return diff.getLeftSide() != null && diff.getRightSide() != null;
    }

    private ContentStatus findContentStatus(Diff diff) {

        String left = retrieveJsonStringFromBase64Encoded(diff.getLeftSide());
        String right = retrieveJsonStringFromBase64Encoded(diff.getRightSide());

        if (left.equals(right))
            return ContentStatus.EQUAL;

        if (left.length() != right.length())
            return ContentStatus.DIFFERENT_SIZES;

        return ContentStatus.SAME_SIZE_DIFFERENT_CONTENT;
    }

    private String retrieveJsonStringFromBase64Encoded(String base64EncodedValue) {
        return new String(Base64.getDecoder().decode(base64EncodedValue));
    }

    private String findDifferenceBetweenLeftAndRight(Diff diff) {
        //obtain the JSON as byte array
        byte[] left = retrieveJsonStringFromBase64Encoded(diff.getLeftSide()).getBytes();
        byte[] right = retrieveJsonStringFromBase64Encoded(diff.getRightSide()).getBytes();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < right.length; i++) {
            //if there is any different
            if (left[i] != right[i]) {
                // append the position to StringBuilder
                stringBuilder.append(String.format("%d ", i));
            }
        }
        return stringBuilder.toString().trim();
    }
    
}
