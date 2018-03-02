package com.kousenit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RestServiceApplicationTests {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    private Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void addDeveloper() throws Exception {
        Developer d = new Developer(1, "Fred");
        System.out.println(gson.toJson(d));
        mvc.perform(post("/dev").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(d)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Fred"));
    }
}
