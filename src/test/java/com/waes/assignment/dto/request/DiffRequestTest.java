package com.waes.assignment.dto.request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests of DiffRequest object's tests
 *
 * @developer -- ilkercolakoglu
 */
public class DiffRequestTest {

    private static final String BASE_64_ENCODED_JSON = "ewoibmFtZSI6Imlsa2VyIiwKIm1vYmlsZSI6IjkwNTUzMjM4MTA5NCIsCiJ0ZWFtIjoid2FlcyIsCiJhZ2UiOjI5Cn0=";

    private DiffRequest diffRequest;

    @Before
    public void setup() {
        diffRequest = new DiffRequest();
        diffRequest.setBase64encodedJSON(BASE_64_ENCODED_JSON);
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(diffRequest.hashCode());
    }

    @Test
    public void toStringTest() {
        assertNotNull(diffRequest.toString());
    }

    @Test
    public void equalsTest() {
        DiffRequest documentToCompare = createDiffRequest();
        DiffRequest nullRequest = null;
        Object nullObject = null;
        assertTrue(documentToCompare.equals(diffRequest) && diffRequest.equals(documentToCompare));
        assertFalse(diffRequest.equals(nullRequest));
        assertFalse(diffRequest.equals(nullObject));
    }

    private DiffRequest createDiffRequest() {
        DiffRequest diffRequest = new DiffRequest();
        diffRequest.setBase64encodedJSON(BASE_64_ENCODED_JSON);
        return diffRequest;
    }
}