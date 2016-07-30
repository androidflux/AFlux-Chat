package com.example.flux.android.domain.impl;

import com.example.flux.android.domain.ChatService;
import com.example.flux.android.model.Message;
import com.example.flux.android.model.Thread;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ntop on 16/7/29.
 */
public class ChatServiceTest {

    @Test
    public void test_getAllThread() {
        ChatService chatService = new ChatServiceImpl();
        List<Message> threads = chatService.getAllMessage();
        assertEquals(100, threads.size());
    }
}