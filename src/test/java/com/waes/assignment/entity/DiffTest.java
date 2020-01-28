package com.waes.assignment.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests of Diff entity's tests
 *
 * @developer -- ilkercolakoglu
 */
public class DiffTest {

    private Diff diff;

    @Before
    public void setup() {
        diff = new Diff();
        diff.setId(1L);
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(diff.hashCode());
    }

    @Test
    public void toStringTest() {
        assertNotNull(diff.toString());
    }

    @Test
    public void equalsTest() {
        Diff documentToCompare = createDiff();
        Diff nullRequest = null;
        Object nullObject = null;
        assertTrue(documentToCompare.equals(diff) && diff.equals(documentToCompare));
        assertFalse(diff.equals(nullRequest));
        assertFalse(diff.equals(nullObject));
    }

    private Diff createDiff() {
        Diff diff = new Diff();
        diff.setId(1L);
        return diff;
    }
    
}