package com.example.flux.android.model;

/**
 * 在对话列表中，每个Item必须的显示信息
 * Created by ntop on 18/12/15.
 */
public class Thread {
    // 标志对话的唯一Id
    private String id;

    // 对话对方头像
    private int avatar;

    // 对话对方名称
    private String title;

    // 是否包含未读信息
    private boolean isRead = false;

    // 最后一条信息
    private Message lastMessage;

    public Thread(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return lastMessage.getMessage();
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
}
