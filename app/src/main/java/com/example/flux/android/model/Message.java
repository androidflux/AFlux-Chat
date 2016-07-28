package com.example.flux.android.model;

/**
 * 消息列表中，显示每条消息必须的信息
 * Created by ntop on 16/7/27.
 */
public class Message {
    // 消息类型是接收消息还是回复消息 0: from , 1:to
    private int type = 0;
    // 对话Id
    private String threadId;
    // 头像
    private int avatar;
    // 消息
    private String message;
    // 是否已读
    private boolean isRead;
    // 时间戳
    private long timestamp;

    public Message(){}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
