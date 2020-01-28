package com.waes.assignment.service.impl;

import com.waes.assignment.dto.request.DiffRequest;
import com.waes.assignment.dto.response.DiffResponse;
import com.waes.assignment.entity.Diff;
import com.waes.assignment.enums.ContentStatus;
import com.waes.assignment.exception.BothSideNotFoundException;
import com.waes.assignment.exception.DiffNotFoundException;
import com.waes.assignment.exception.InvalidSideException;
import com.waes.assignment.repository.DiffRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for all logic implementations in DiffServiceImpl
 *
 * @developer -- ilkercolakoglu
 */
@RunWith(SpringRunner.class)
public class DiffServiceImplTest {

    @InjectMocks
    private DiffServiceImpl diffService;

    @Mock
    private DiffRepository diffRepository;

    private final String first = "{\n" +
            "\"job\":\"basketball player\",\n" +
            "\"jerseyNumber\":\"24\",\n" +
            "\"name\":\"kobe\",\n" +
            "\"surname\":\"bryant\",\n" +
            "\"nickname\":\"mamba8\"\n" +
            "}";
    private final String second = "{\n" +
            "\"job\":\"basketball player\",\n" +
            "\"jerseyNumber\":\"24\",\n" +
            "\"name\":\"kobe\",\n" +
            "\"surname\":\"bryant\",\n" +
            "\"nickname\":\"legend\"\n" +
            "}";
    private final String third = "{\n" +
            "\"job\":\"basketball player\",\n" +
            "\"jerseyNumber\":\"24\",\n" +
            "\"name\":\"kobe\",\n" +
            "\"surname\":\"bryant\",\n" +
            "\"nickname\":\"mamba\",\n" +
            "\"team\":{\n" +
            "\"name\":\"lakers\",\n" +
            "\"city\":\"los angeles\",\n" +
            "\"championshipsCount\":16\n" +
            "}\n" +
            "}";

    private String encodedJsonA;

    private String encodedJsonB;

    private String encodedJsonC;

    @Before
    public void setUp() {
        encodedJsonA = Base64.getEncoder().encodeToString(first.getBytes());
        encodedJsonB = Base64.getEncoder().encodeToString(second.getBytes());
        encodedJsonC = Base64.getEncoder().encodeToString(third.getBytes());
    }


    @Test
    public void should_save_diff()  {
        DiffRequest diffRequest = new DiffRequest();

        Optional<Diff> diffOptional=Optional.of(createEqualDiff());

        when(diffRepository.findById(1L)).thenReturn(diffOptional);

        // must catch in order to avoid a compilation error
        try {
            assertTrue(diffService.save(diffRequest, 1L, "left"));
        } catch (InvalidSideException e) {
            fail("should_save_diff exception is occurred");
        }

        try {
            assertTrue(diffService.save(diffRequest, 1L, "right"));
        } catch (InvalidSideException e) {
            fail("should_save_diff exception is occurred");
        }
    }

    @Test(expected = InvalidSideException.class)
    public void should_return_invalid_side_exception() throws InvalidSideException {
        DiffRequest diffRequest = new DiffRequest();

        Optional<Diff> diffOptional=Optional.of(createEqualDiff());

        when(diffRepository.findById(1L)).thenReturn(diffOptional);

        diffService.save(diffRequest, 1L, "invalid_side");
    }


    @Test
    public void should_get_difference_equal_content() {
        Optional<Diff> diffEqualOptional=Optional.of(createEqualDiff());

        when(diffRepository.findById(1L)).thenReturn(diffEqualOptional);

        // must catch in order to avoid a compilation error
        try {
            assertEquals(diffService.getDifference( 1L).getStatus(),createEqualDiffResponse().getStatus());
        } catch (BothSideNotFoundException | DiffNotFoundException e) {
            fail("should_save_diff exception is occurred");
        }
    }

    @Test
    public void should_get_difference_same_size_different_content() {
        Optional<Diff> diffSameSizeDifferentContentOptional=Optional.of(createSameSizeDifferentContent());

        when(diffRepository.findById(1L)).thenReturn(diffSameSizeDifferentContentOptional);

        // must catch in order to avoid a compilation error
        try {
            assertEquals(diffService.getDifference( 1L).getStatus(),createSameSizeDifferentContentDiffResponse().getStatus());
            assertNotNull(diffService.getDifference(1L).getLength());
            assertNotNull(diffService.getDifference(1L).getOffsets());
        } catch (BothSideNotFoundException | DiffNotFoundException e) {
            fail("should_save_diff exception is occurred");
        }
    }

    @Test
    public void should_get_difference_different_size() {
        Optional<Diff> diffDifferentSizeOptional=Optional.of(createDifferentSize());

        when(diffRepository.findById(1L)).thenReturn(diffDifferentSizeOptional);

        // must catch in order to avoid a compilation error
        try {
            assertEquals(diffService.getDifference( 1L).getStatus(),createDifferentSizeDiffResponse().getStatus());
        } catch (BothSideNotFoundException | DiffNotFoundException e) {
            fail("should_save_diff exception is occurred");
        }
    }

    @Test(expected = DiffNotFoundException.class)
    public void should_get_diff_not_found() throws BothSideNotFoundException, DiffNotFoundException {
        when(diffRepository.findById(1L)).thenReturn(Optional.empty());

        diffService.getDifference(1L);
    }

    @Test(expected = BothSideNotFoundException.class)
    public void should_get_both_side_not_found_exception() throws BothSideNotFoundException, DiffNotFoundException {
        Optional<Diff> optionalDiffWithOneSide=Optional.of(createDiffWithOneSide());
        when(diffRepository.findById(1L)).thenReturn(optionalDiffWithOneSide);

        diffService.getDifference(1L);
    }

    private Diff createEqualDiff(){
        Diff diff=new Diff();
        diff.setId(1L);
        diff.setLeftSide(encodedJsonA);
        diff.setRightSide(encodedJsonA);
        return diff;
    }

    private Diff createSameSizeDifferentContent(){
        Diff diff=new Diff();
        diff.setId(1L);
        diff.setLeftSide(encodedJsonA);
        diff.setRightSide(encodedJsonB);
        return diff;
    }

    private Diff createDifferentSize(){
        Diff diff=new Diff();
        diff.setId(1L);
        diff.setLeftSide(encodedJsonA);
        diff.setRightSide(encodedJsonC);
        return diff;
    }

    private DiffResponse createEqualDiffResponse(){
        return DiffResponse.builder().status(ContentStatus.EQUAL).build();
    }

    private DiffResponse createSameSizeDifferentContentDiffResponse(){
        return DiffResponse.builder().status(ContentStatus.SAME_SIZE_DIFFERENT_CONTENT).build();
    }

    private DiffResponse createDifferentSizeDiffResponse(){
        return DiffResponse.builder().status(ContentStatus.DIFFERENT_SIZES).build();
    }

    private Diff createDiffWithOneSide(){
        Diff diff=new Diff();
        diff.setId(1L);
        diff.setLeftSide(encodedJsonA);
        return diff;
    }

}