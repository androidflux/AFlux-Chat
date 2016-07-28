package com.example.flux.android.stores;

import android.util.Log;

import com.example.flux.android.actions.Action;
import com.example.flux.android.actions.ChatAction;
import com.example.flux.android.model.Message;
import com.example.flux.android.model.Thread;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

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
            case ChatAction.CHAT_INIT_THREAD:
            {
                threads = (List<Thread>)action.getData();
                emitStoreChange();
            }
            break;
            case ChatAction.CHAT_CLICK_THREAD:
            {
                Thread th = (Thread)action.getData();
                for(Thread thread : threads) {
                    if (thread.getId().equals(th.getId())) {
                        thread.setRead(true);
                        break;
                    }
                }
                emitStoreChange("nav");
            }break;
            case ChatAction.CHAT_NEW_THREAD:
            {
                Thread thread = (Thread)action.getExtra("thread");
                for(Thread th : threads) {
                    if (th.getTitle().equals(thread.getTitle())) {
                        thread.setRead(true);
                        break;
                    }
                }
                emitStoreChange();
            }
            break;
            case ChatAction.CHAT_SEND_MESSAGE:
            {
                Message message = (Message)action.getData();
                for (Thread thread : threads) {
                    if (thread.getId().equals(message.getThreadId())) {
                        thread.setLastMessage(message);
                        break;
                    }
                }
                emitStoreChange();
            }
            break;
            case ChatAction.CHAT_RECEV_MESSAGE:
            {
                Message message = (Message)action.getData();
                for (Thread thread : threads) {
                    if (thread.getTitle().equals(message.getThreadId())) {
                        thread.setLastMessage(message);
                        break;
                    }
                }
                emitStoreChange();
            }
            break;
            case ChatAction.CHAT_RECEV_BATCH:
            {

            }
            break;
            default:
        }
    }

    private void receive(Message message) {

    }

    @Override
    public StoreChangeEvent changeEvent() {
        return change;
    }
}
