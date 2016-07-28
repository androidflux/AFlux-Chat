package com.example.flux.android.stores;

import android.util.Log;

import com.example.flux.android.Flux;
import com.example.flux.android.actions.Action;
import com.example.flux.android.actions.ChatAction;
import com.example.flux.android.actions.ChatActionCreator;
import com.example.flux.android.dispatcher.Dispatcher;
import com.example.flux.android.model.*;
import com.example.flux.android.model.Thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ntop on 16/7/27.
 */
public class MessageStore extends Store {
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
            case ChatAction.CHAT_CLICK_THREAD:
            {
                Thread thread = (Thread)action.getData();
                threadId = thread.getId();
                title = thread.getTitle();
                emitStoreChange();
            }break;
            case ChatAction.CHAT_INIT_MESSAGE:
            {
                messages = (List<Message>) action.getData();
                unRead = (int)action.getExtra("unread");
                emitStoreChange();
            }break;
            case ChatAction.CHAT_SEND_MESSAGE:
            {
                Message message = (Message)action.getData();
                messages.add(message);
                emitStoreChange();
            }break;
            case ChatAction.CHAT_RECEV_BATCH:
            {
                List<Message> data = (List<Message>)action.getData();
                for(Message message : data) {
                    if (threadId.equals(message.getThreadId())) {
                        message.setRead(true);
                        messages.add(message);
                    } else {
                        unRead ++;
                    }
                }
                emitStoreChange();
            }break;
            case ChatAction.CHAT_RECEV_MESSAGE:
            {
                Message message = (Message)action.getData();
                if (threadId.equals(message.getThreadId())) {
                    message.setRead(true);
                    messages.add(message);
                } else {
                    unRead ++;
                }
                emitStoreChange();
            }
            break;
            case ChatAction.CHAT_NEW_THREAD:
            {
                threadId = (String)action.getExtra("thread");
                unRead = (int)action.getExtra("unread");
                title = (String)action.getExtra("title");
                emitStoreChange();
            }
            break;

        }
    }
}
