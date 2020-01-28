package com.waes.assignment.dto.response;

import com.waes.assignment.enums.ContentStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests of DiffResponse object's tests
 *
 * @developer -- ilkercolakoglu
 */
public class DiffResponseTest {

    private DiffResponse diffResponse;

    @Before
    public void setup() {
        diffResponse = DiffResponse.builder()
                .status(ContentStatus.EQUAL)
                .length(25)
                .offsets("offset").build();
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(diffResponse.hashCode());
    }

    @Test
    public void toStringTest() {
        assertNotNull(diffResponse.toString());
    }

    @Test
    public void equalsTest() {
        DiffResponse documentToCompare = createDiffResponse();
        DiffResponse nullResponse = null;
        Object nullObject = null;
        assertTrue(documentToCompare.equals(diffResponse) && diffResponse.equals(documentToCompare));
        assertFalse(diffResponse.equals(nullResponse));
        assertFalse(diffResponse.equals(nullObject));
    }

    private DiffResponse createDiffResponse() {
        return DiffResponse.builder()
                .status(ContentStatus.EQUAL)
                .length(25)
                .offsets("offset").build();
    }
}