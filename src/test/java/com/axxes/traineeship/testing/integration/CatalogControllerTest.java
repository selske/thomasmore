package com.axxes.traineeship.testing.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogController.class)
@RunWith(SpringRunner.class)
public class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCatalogs() throws Exception {
        mockMvc.perform(get("/api/catalogs")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                                          "  {\n" +
                                          "    \"name\": \"group_1\",\n" +
                                          "    \"children\": [\n" +
                                          "      {\n" +
                                          "        \"name\": \"name_1\",\n" +
                                          "        \"children\": [],\n" +
                                          "        \"fullName\": \"group_1/name_1\"\n" +
                                          "      },\n" +
                                          "      {\n" +
                                          "        \"name\": \"name_2\",\n" +
                                          "        \"children\": [],\n" +
                                          "        \"fullName\": \"group_1/name_2\"\n" +
                                          "      }\n" +
                                          "    ],\n" +
                                          "    \"fullName\": \"group_1\"\n" +
                                          "  },\n" +
                                          "  {\n" +
                                          "    \"name\": \"group_2\",\n" +
                                          "    \"children\": [\n" +
                                          "      {\n" +
                                          "        \"name\": \"name_1\",\n" +
                                          "        \"children\": [],\n" +
                                          "        \"fullName\": \"group_2/name_1\"\n" +
                                          "      }\n" +
                                          "    ],\n" +
                                          "    \"fullName\": \"group_2\"\n" +
                                          "  }\n" +
                                          "]"));
    }

}
