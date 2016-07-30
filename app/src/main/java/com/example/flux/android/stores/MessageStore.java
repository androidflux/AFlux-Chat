package com.example.flux.android.stores;

import com.example.flux.android.actions.Action;
import com.example.flux.android.actions.ChatAction;
import com.example.flux.android.model.*;
import com.example.flux.android.model.Thread;
import com.example.flux.android.utils.utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by ntop on 16/7/27.
 */
public class MessageStore extends Store {
    private List<Message> all = Collections.EMPTY_LIST;
    private List<Message> messages = Collections.EMPTY_LIST;
    private String threadId;
    private String title;
    private int unRead = 0;

    public MessageStore(){
        super();
    }

    public List<Message> getThreads() {
        return messages;
    }

    public int getUnRead() {
        return unRead;
    }

    public String getBackText() {
        if (unRead > 0) {
            return String.format("Back(%d)", unRead);
        } else {
            return "Back";
        }
    }

    public String getTitle() {
        return title;
    }

    public String getThreadId() {
        return threadId;
    }

    @Override
    public StoreChangeEvent changeEvent() {
        return new StoreChangeEvent();
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()) {
            case ChatAction.CHAT_RECEV_BATCH:
            {
                this.all = ((List<Message>) action.getData());
                this.unRead = utils.sum(this.all, new utils.Filter<Message>() {
                    @Override
                    public boolean apply(Message message) {
                        return !message.isRead();
                    }
                });
                emitStoreChange();
            }break;
            case ChatAction.CHAT_CLICK_THREAD:
            {
                initThread((Thread)action.getData());
                emitStoreChange();
            }break;
            case ChatAction.CHAT_SEND_MESSAGE:
            case ChatAction.CHAT_RECEV_MESSAGE:
            {
                Message message = (Message)action.getData();
                messages.add(message);
                all.add(message);
                emitStoreChange();
            }break;
        }
    }

    private void initThread(final Thread thread) {
        threadId = thread.getId();
        title = thread.getTitle();
        messages = utils.findAll(all, new utils.Filter<Message>() {
            @Override
            public boolean apply(Message message) {
                return (message.getThreadId().equals(thread.getId()));
            }
        });
        unRead -= utils.sum(messages, new utils.Filter<Message>() {
            @Override
            public boolean apply(Message message) {
                return !message.isRead();
            }
        });
        messages = utils.map(messages, new utils.Map<Message, Message>(){
            @Override
            public Message convert(Message message) {
                message.setRead(true);
                return message;
            }
        });
    }
}
