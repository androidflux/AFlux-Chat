package com.example.flux.android.domain.utils;

import com.example.flux.android.domain.model.RawMessage;
import com.example.flux.android.domain.model.RawPerson;
import com.example.flux.android.model.Message;
import com.example.flux.android.model.Person;

/**
 * Created by ntop on 16/7/28.
 */
public class ChatUtil {
    public static Message convert(RawMessage rawMessage) {
        Message message = new Message();
        message.setRead(rawMessage.isRead);
        message.setAvatar(rawMessage.author.getAvatar());
        message.setMessage(rawMessage.message);
        message.setThreadId(rawMessage.threadId);
        message.setType(rawMessage.type);
        return message;
    }

    public static Person convert(RawPerson rawPerson) {
        Person person = new Person();
        person.setAvatar(rawPerson.avatar);
        person.setName(rawPerson.name);
        person.setId(rawPerson.id);
        return person;
    }
}
