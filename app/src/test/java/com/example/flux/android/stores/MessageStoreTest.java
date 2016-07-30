package com.example.flux.android.stores;

import com.example.flux.android.actions.Action;
import com.example.flux.android.actions.ChatAction;
import com.example.flux.android.model.Message;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by ntop on 16/7/30.
 */
public class MessageStoreTest {

    @Test
    public void testAction_CHAT_RECEV_BATCH() {
        // setup
        MessageStore messageStore = new MessageStore();
        List<Message> messages = mockMessage();
        // replay
        messageStore.onAction(new Action(ChatAction.CHAT_RECEV_BATCH, messages));
        // assert
        assertEquals(messages.size(), messageStore.getUnRead());
    }

    @Test
    public void testAction_CHAT_CLICK_THREAD() {

    }

    @Test
    public void testAction_CHAT_RECEV_MESSAGE() {

    }

    private List<Message> mockMessage() {
        List<Message> list = new ArrayList<>();
        for(int i =0; i<100;i++) {
            list.add(new Message());
        }
        return list;
    }
}
