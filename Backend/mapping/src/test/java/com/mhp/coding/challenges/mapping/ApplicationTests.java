package com.mhp.coding.challenges.mapping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    // acceptance criterion 1 - returns json data
    @Test
    public void shouldReturnAllArticles() throws Exception {
        this.mockMvc.perform(get("/article"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Article Description 1001")))
                .andExpect(content().string(containsString("Article Description 2002")))
                .andExpect(content().string(containsString("Article Description 3003")))
                .andExpect(content().string(containsString("Article Description 4004")))
                .andExpect(content().string(containsString("Article Description 5005")));
    }

    // acceptance criterion 1 - returns json data
    @Test
    public void shouldReturnSingleArticle() throws Exception {
        this.mockMvc.perform(get("/article/1001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Article Description 1001")));
    }

    // acceptance criterion 3 - returns 404 for unknown id
    @Test
    public void shouldReturnNotFoundForUnknownId() throws Exception {
        this.mockMvc.perform(get("/article/9999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
