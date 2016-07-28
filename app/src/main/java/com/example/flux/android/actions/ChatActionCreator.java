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

    public void loadAllThread() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ChatService chatService = Factory.getChatService();
                List<Thread> threads = chatService.getAllThread();
                dispatcher.dispatch(new ChatAction(ChatAction.CHAT_INIT_THREAD, threads));
            }
        }, 1000);
    }

    public void clickThread(final Thread thread) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dispatcher.dispatch(new ChatAction(ChatAction.CHAT_CLICK_THREAD, thread));
            }
        }, 1000);
    }

    public void loadMessage(final String threadId) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ChatService chatService = Factory.getChatService();
                List<Message> messages = chatService.getAllMessage(threadId);
                dispatcher.dispatch(new ChatAction(ChatAction.CHAT_INIT_MESSAGE, messages));
            }
        }, 1000);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ChatService chatService = Factory.getChatService();
                chatService.pushMessage(message, onself);
            }
        }, 1000);
    }

}
