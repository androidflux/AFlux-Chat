package com.example.flux.android.domain.impl;

import com.example.flux.android.domain.ChatService;
import com.example.flux.android.domain.Factory;
import com.example.flux.android.domain.model.RawMessage;
import com.example.flux.android.domain.utils.ChatUtil;
import com.example.flux.android.model.*;
import com.example.flux.android.model.Thread;
import com.example.flux.android.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 消息服务的领域模型, 这里仅作简答的实现。
 * Created by ntop on 16/7/28.
 */
public class ChatServiceImpl implements ChatService {
    private List<RawMessage> messages = new ArrayList<RawMessage>();

    @Override
    public List<Thread> getAllThread() {
        Map<String, Thread> temp = new HashMap<>();
        for(RawMessage message: messages) {
            if (!temp.containsKey(message.threadId)) {
                Thread thread = new Thread();
                thread.setId(message.threadId);
                thread.setTitle(message.author.getName());
                thread.setAvatar(message.author.getAvatar());
                thread.setLastMessage(ChatUtil.convert(message));

                if (!message.isRead) thread.setRead(false);
                temp.put(message.threadId, thread);
            } else {
                Thread thread = temp.get(message.threadId);
                if (thread.getLastMessage().getTimestamp() < message.timestamp) {
                    thread.setLastMessage(ChatUtil.convert(message));
                }
            }
        }
        return new ArrayList<>(temp.values());
    }

    @Override
    public List<Message> getAllMessage(String threadId) {
        List<Message> list = new ArrayList<>();
        for(RawMessage rawMessage : messages) {
            if (rawMessage.threadId.equals(threadId)) {
                list.add(ChatUtil.convert(rawMessage));
            }
        }
        return list;
    }

    @Override
    public void pushMessage(Message message, Person person) {
        RawMessage raw = new RawMessage();
        raw.message = message.getMessage();
        raw.threadId = message.getThreadId();
        raw.timestamp = message.getTimestamp();
        raw.isRead = message.isRead();
        raw.type = message.getType();
        raw.author = person;
        messages.add(raw);
    }

    // 伪造一些数据
    private String[] data = {
        "春江潮水连海平", "海上明月共潮生",
        "滟滟随波千万里", "何处春江无月明",
        "江流宛转绕芳甸", "月照花林皆似霰",
        "空里流霜不觉飞", "汀上白沙看不见",
        "江天一色无纤尘", "皎皎空中孤月轮",
        "江畔何人初见月", "江月何年初照人" ,
        "人生代代无穷已", "江月年年望相似",
        "不知江月待何人", "但见长江送流水",
        "白云一片去悠悠", "青枫浦上不胜愁",
        "谁家今夜扁舟子", "何处相思明月楼",
        "可怜楼上月徘徊", "应照离人妆镜台",
        "玉户帘中卷不去", "捣衣砧上拂还来",
        "此时相望不相闻", "愿逐月华流照君",
        "鸿雁长飞光不度", "鱼龙潜跃水成文",
        "昨夜闲潭梦落花", "可怜春半不还家",
        "江水流春去欲尽", "江潭落月复西斜",
        "斜月沉沉藏海雾", "碣石潇湘无限路",
        "不知乘月几人归", "落月摇情满江树"
    };
    {
        List<Person> persons = Factory.getContactService().getAll();
        for(int i=0; i<100; i++) {
            RawMessage rawMessage = new RawMessage();
            rawMessage.author = persons.get(new Random().nextInt(3));
            rawMessage.isRead = new Random().nextBoolean();
            rawMessage.message = data[i%data.length];
            rawMessage.threadId = rawMessage.author.getId();
            rawMessage.timestamp = new Random().nextLong();
            rawMessage.type = new Random().nextInt(2);

            messages.add(rawMessage);
        }
    }
}
