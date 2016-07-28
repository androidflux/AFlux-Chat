package com.example.flux.android.domain.model;

import com.example.flux.android.model.Person;

/**
 * Created by ntop on 16/7/28.
 */
public class RawMessage {
    // 0: from , 1:to
    public int type = 0;
    // 对话Id
    public String threadId;
    // 消息
    public String message;

    public boolean isRead;

    public long timestamp;

    public Person author;
}
