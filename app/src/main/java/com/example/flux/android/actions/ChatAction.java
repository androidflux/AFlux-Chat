package com.example.flux.android.actions;

/**
 * Created by ntop on 16/7/28.
 */
public class ChatAction<T> extends Action<T> {
    public static final String CHAT_CLICK_THREAD = "chat/click_thread";
    public static final String CHAT_SEND_MESSAGE = "chat/send_message";
    public static final String CHAT_RECEV_MESSAGE = "chat/recev_message";
    public static final String CHAT_RECEV_BATCH = "chat/recev_batch";

    public ChatAction(String type, T data) {
        super(type, data);
    }
}
