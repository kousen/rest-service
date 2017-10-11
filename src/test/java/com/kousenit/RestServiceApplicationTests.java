package com.kousenit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestServiceApplicationTests {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void addDeveloper() throws Exception {
        mvc.perform(post("/dev").content("Fred"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Fred"));
    }

    @Test
    public void getDeveloper() throws Exception {
        MvcResult result = mvc.perform(post("/dev").content("Fred"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        mvc.perform(get("/dev/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fred"))
                .andExpect(jsonPath("$.id").value("1"));
    }
}
