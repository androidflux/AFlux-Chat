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
        List<Thread> threads = chatService.getAllThread();
        assertEquals(3, threads.size());

        List<Message> m1 = chatService.getAllMessage(threads.get(0).getId());
        List<Message> m2 = chatService.getAllMessage(threads.get(1).getId());
        List<Message> m3 = chatService.getAllMessage(threads.get(2).getId());

        assertTrue(m1.size() > 0);
        assertTrue(m2.size() > 0);
        assertTrue(m3.size() > 0);
    }
}