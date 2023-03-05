package com.example.controllers;

import com.example.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.config.SpringConfig.TEST_PROFILE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfig.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getNotes() throws Exception {
        final var response = mockMvc.perform(get("/api/notes"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("notes"))
            .andReturn()
            .getResponse();

        assertTrue(response.getContentAsString().contains("Создать заметку"));
        assertTrue(response.getContentAsString().contains("1Description"));
    }

    @Test
    void getNote() throws Exception {
        final var response = mockMvc.perform(get("/api/notes/2"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("note"))
            .andReturn()
            .getResponse();

        assertTrue(response.getContentAsString().contains("2Description"));
        assertTrue(response.getContentAsString().contains("Заметка"));
        assertTrue(response.getContentAsString().contains("Текст заметки"));
    }

    @Test
    void getCreateNote() throws Exception {
        final var response = mockMvc.perform(get("/api/notes/new"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("new"))
            .andReturn()
            .getResponse();

        assertTrue(response.getContentAsString().contains("Создать новую заметку"));
    }

    @Test
    void getUpdateNote() throws Exception {
        final var response = mockMvc.perform(get("/api/notes/3/edit"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("edit"))
            .andReturn()
            .getResponse();

        assertTrue(response.getContentAsString().contains("Редактировать заметку"));
    }

    @Test
    void createNote() throws Exception {
        mockMvc.perform(post("/api/notes/new")
                .param("header", "newHeader")
                .param("description", "newDescription"))
            .andExpect(status().is3xxRedirection());

        final var response = mockMvc.perform(get("/api/notes"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("notes"))
            .andReturn()
            .getResponse();

        assertTrue(response.getContentAsString().contains("newDescription"));
    }

    @Test
    void updateNote() throws Exception {
        mockMvc.perform(put("/api/notes/4/edit")
                .param("header", "updateHeader")
                .param("description", "updateDescription"))
            .andExpect(status().is3xxRedirection());

        final var response = mockMvc.perform(get("/api/notes"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("notes"))
            .andReturn()
            .getResponse();

        assertTrue(response.getContentAsString().contains("updateDescription"));
    }

    @Test
    void deleteNote() throws Exception {
        mockMvc.perform(delete("/api/notes/5"))
            .andExpect(status().is3xxRedirection());

        final var response = mockMvc.perform(get("/api/notes"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("notes"))
            .andReturn()
            .getResponse();

        assertFalse(response.getContentAsString().contains("5Description"));
    }

}
