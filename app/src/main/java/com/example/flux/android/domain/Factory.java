package com.example.flux.android.domain;

import com.example.flux.android.domain.impl.ChatServiceImpl;
import com.example.flux.android.domain.impl.ContactServiceImpl;

/**
 * Created by ntop on 16/7/28.
 */
public class Factory {
    private static ChatService chatService = null;
    private static ContactService contactService = null;

    public static ChatService getChatService() {
        if (chatService == null) {
            chatService = new ChatServiceImpl();
        }
        return chatService;
    }

    public static ContactService getContactService() {
        if (contactService == null) {
            contactService = new ContactServiceImpl();
        }
        return contactService;
    }
}
