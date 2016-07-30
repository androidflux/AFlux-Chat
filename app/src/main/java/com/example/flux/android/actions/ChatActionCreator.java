package com.example.flux.android.actions;

import com.example.flux.android.dispatcher.Dispatcher;
import com.example.flux.android.domain.ChatService;
import com.example.flux.android.domain.Factory;
import com.example.flux.android.model.*;
import com.example.flux.android.model.Thread;

import android.os.Handler;

import java.util.List;

/**
 * Created by ntop on 16/7/28.
 */
public class ChatActionCreator {
    private static ChatActionCreator actionCreator;
    private Dispatcher dispatcher;

    public static ChatActionCreator getCreator(Dispatcher dispatcher) {
        if (actionCreator == null) {
            actionCreator = new ChatActionCreator(dispatcher);
        }
        return actionCreator;
    }

    public ChatActionCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void receiveBatch() {
        ChatService chatService = Factory.getChatService();
        List<Message> messages = chatService.getAllMessage();
        dispatcher.dispatch(new ChatAction(ChatAction.CHAT_RECEV_BATCH, messages));
    }

    public void clickThread(final Thread thread) {
        dispatcher.dispatch(new ChatAction(ChatAction.CHAT_CLICK_THREAD, thread));
    }

    public void sendMessage(final String text, final String threadId) {
        final Person onself = Factory.getContactService().oneself();
        final Message message = new Message();
        message.setMessage(text);
        message.setThreadId(threadId);
        message.setAvatar(onself.getAvatar());
        message.setRead(true);
        message.setType(1);
        message.setTimestamp(System.currentTimeMillis());

        dispatcher.dispatch(new ChatAction(ChatAction.CHAT_SEND_MESSAGE, message));
    }
}
