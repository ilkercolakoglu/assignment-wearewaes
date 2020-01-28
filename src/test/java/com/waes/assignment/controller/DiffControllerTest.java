package com.waes.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.assignment.dto.request.DiffRequest;
import com.waes.assignment.entity.Diff;
import com.waes.assignment.repository.DiffRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.SecureRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Integration tests of diff API for all responses
 *
 * @developer -- ilkercolakoglu
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DiffControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiffRepository diffRepository;

    private ObjectMapper objectMapper;

    private static final String LEFT_SIDE_ENDPOINT = "/v1/diff/1/left";

    private static final String RIGHT_SIDE_ENDPOINT = "/v1/diff/1/right";

    private static final String INVALID_SIDE_ENDPOINT = "/v1/diff/1/invalid";

    private static final String BASE_64_ENCODED_JSON = "ewoiam9iIjoiYmFza2V0YmFsbCBwbGF5ZXIiLAoiamVyc2V5TnVtYmVyIjoiMjQiLAoibmFtZSI6ImtvYmUiLAoic3VybmFtZSI6ImJyeWFudCIsCiJuaWNrbmFtZSI6Im1hbWJhIiwKInRlYW0iOnsKIm5hbWUiOiJsYWtlcnMiLAoiY2l0eSI6ImxvcyBhbmdlbGVzIiwKImNoYW1waW9uc2hpcHNDb3VudCI6MTYKfQp9";

    private static final String GET_DIFF_ENDPOINT = "/v1/diff/1";

    private static final String GET_NOT_FOUND_DIFF_ENDPOINT = "/v1/diff/2";

    @Before
    public void setUp() throws Exception {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void should_save_left_side() throws Exception {
        DiffRequest request = new DiffRequest();
        request.setBase64encodedJSON(BASE_64_ENCODED_JSON);

        String requestBody = this.objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post(LEFT_SIDE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_save_right_side() throws Exception {
        DiffRequest request = new DiffRequest();
        request.setBase64encodedJSON(BASE_64_ENCODED_JSON);

        String requestBody = this.objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post(RIGHT_SIDE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_unprocessable_entity() throws Exception {
        DiffRequest request = new DiffRequest();
        request.setBase64encodedJSON(BASE_64_ENCODED_JSON);

        String requestBody = this.objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post(INVALID_SIDE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_return_validation_error_for_empty_value() throws Exception {
        DiffRequest request = new DiffRequest();
        request.setBase64encodedJSON(generateRandomString(0));

        String requestBody = this.objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post(LEFT_SIDE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_validation_error_for_max_value() throws Exception {
        DiffRequest request = new DiffRequest();
        request.setBase64encodedJSON(generateRandomString(10001));

        String requestBody = this.objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post(LEFT_SIDE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_404_not_found_when_diff_not_found() throws Exception {
        this.mockMvc.perform(get(GET_NOT_FOUND_DIFF_ENDPOINT))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_422_when_one_side_is_null() throws Exception {
        Diff diff = new Diff(1L);
        diff.setLeftSide(BASE_64_ENCODED_JSON);

        diffRepository.save(diff);

        this.mockMvc.perform(get(GET_DIFF_ENDPOINT))
                .andExpect(status().isPreconditionRequired());
    }

    @Test
    public void should_return_diff() throws Exception {
        Diff diff = new Diff(1L);
        diff.setLeftSide(BASE_64_ENCODED_JSON);
        diff.setRightSide(BASE_64_ENCODED_JSON);

        diffRepository.save(diff);

        this.mockMvc.perform(get(GET_DIFF_ENDPOINT))
                .andExpect(status().isOk());
    }



    private static String generateRandomString(int len) {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";
        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }
}