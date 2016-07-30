package com.example.flux.android.stores;

import com.example.flux.android.actions.Action;
import com.example.flux.android.actions.ChatAction;
import com.example.flux.android.model.Message;
import com.example.flux.android.model.Thread;
import com.example.flux.android.utils.utils;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MessageStore类主要用来维护MainActivity的UI状态
 * Created by ntop on 18/12/15.
 */
public class ThreadStore extends Store {
    private StoreChangeEvent change = new StoreChangeEvent();
    private List<Thread> threads = new ArrayList<Thread>();

    public ThreadStore() {
        super();
    }

    public List<Thread> getThreads() {
        return this.threads;
    }

    @Override
    @Subscribe
    public void onAction(Action action) {
        switch (action.getType()) {
            case ChatAction.CHAT_RECEV_BATCH:
            {
                init((List<Message>) action.getData());
                emitStoreChange();
            }break;
            case ChatAction.CHAT_CLICK_THREAD:
            {
                final Thread th = (Thread)action.getData();
                Thread thread = findById(th.getId());
                thread.setRead(true);
                emitStoreChange("nav");
            }break;
            case ChatAction.CHAT_SEND_MESSAGE:
            case ChatAction.CHAT_RECEV_MESSAGE:
            {
                final Message message = (Message)action.getData();
                Thread thread = findById(message.getThreadId());
                if (thread != null) {
                    thread.setLastMessage(message);
                    emitStoreChange();
                }
            }break;
            default:
        }
    }

    private void init(List<Message> messages) {
        Map<String, Thread> temp = new HashMap<>();
        for(Message message: messages) {
            if (!temp.containsKey(message.getThreadId())) {
                Thread thread = new Thread();
                thread.setId(message.getThreadId());
                thread.setTitle(message.getName());
                thread.setAvatar(message.getAvatar());
                thread.setLastMessage(message);

                if (!message.isRead()) thread.setRead(false);
                temp.put(message.getThreadId(), thread);
            } else {
                Thread thread = temp.get(message.getThreadId());
                if (thread.getLastMessage().getTimestamp() < message.getTimestamp()) {
                    thread.setLastMessage(message);
                }
            }
        }
        threads = new ArrayList<>(temp.values());
    }

    private Thread findById(final String id) {
        return utils.findOne(threads, new utils.Filter<Thread>() {
            @Override
            public boolean apply(Thread thread) {
                return thread.getId().equals(id);
            }
        });
    }

    @Override
    public StoreChangeEvent changeEvent() {
        return change;
    }
}
