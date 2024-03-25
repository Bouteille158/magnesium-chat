package com.sodium.api;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static java.util.Collections.emptyList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sodium.api.controllers.MessageController;
import com.sodium.api.repositories.MessageRepository;

@WebMvcTest(MessageController.class)
public class MessageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    public void getMessagesTest() throws Exception {
        when(messageRepository.findAll()).thenReturn(emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/messages"))
                .andExpect(status().isOk());
    }
}