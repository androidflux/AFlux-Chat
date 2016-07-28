package com.example.flux.android.domain;

import com.example.flux.android.model.Message;
import com.example.flux.android.model.Person;
import com.example.flux.android.model.Thread;

import java.util.List;

/**
 * 消息服务的领域模型
 * Created by ntop on 16/7/28.
 */
public interface ChatService {
    public List<Thread>  getAllThread();
    public List<Message> getAllMessage(String threadId);
    public void pushMessage(Message message, Person person);
}
