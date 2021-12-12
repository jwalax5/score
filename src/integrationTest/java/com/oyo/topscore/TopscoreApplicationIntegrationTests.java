package com.oyo.topscore;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TopscoreApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class TopscoreApplicationIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void putScore_success() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/scores")
                        .content(getScoreRequestBody())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.message", is("Score has been created successfully")))
                .andExpect(jsonPath("$.status", is(200)))
                .andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());

        // check db exist in db
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/scores/" + json.getInt("result"))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.message", is("Get score successfully")))
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.result", notNullValue()))
                .andReturn();
    }

    @Test
    void putScore_failure_negativeScore() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/scores")
                        .content(getInvalidScoreRequestBody())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(jsonPath("$.error[0]", is("Score must be greater than 0")))
                .andExpect(jsonPath("$.status", is(400)))
                .andReturn();
    }

    private String getScoreRequestBody() {
        return "{\n    \"player\":\"testPlayer\",\n    \"score\":60,\n    \"time\":\"2021-12-16 17:08:37\"\n}";
    }

    private String getInvalidScoreRequestBody() {
        return "{\n    \"player\":\"testPlayer\",\n    \"score\":-1,\n    \"time\":\"2021-12-16 17:08:37\"\n}";
    }

}
