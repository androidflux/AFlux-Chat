package com.example.flux.android.domain.impl;

import com.example.flux.android.R;
import com.example.flux.android.domain.ContactService;
import com.example.flux.android.domain.model.RawPerson;
import com.example.flux.android.domain.utils.ChatUtil;
import com.example.flux.android.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ntop on 16/7/29.
 */
public class ContactServiceImpl implements ContactService {
    private List<RawPerson> contact = new ArrayList<>();
    private RawPerson oneself = new RawPerson();
    {
        oneself.id = UUID.randomUUID().toString();
        oneself.name = "AndroidFlux";
        oneself.avatar = R.drawable.flux_logo;
    }

    @Override
    public void create(String name) {
        RawPerson rawPerson = new RawPerson();
        rawPerson.name = name;
        rawPerson.id = UUID.randomUUID().toString();
        contact.add(rawPerson);
    }

    @Override
    public void delete(String id) {
        int pos = -1;
        for(int i = 0; i < contact.size(); i++) {
            if (contact.get(i).id.equals(id)){
                pos = i;
                break;
            }
        }
        if (pos > 0) contact.remove(pos);
    }

    @Override
    public void update(RawPerson person) {
        int pos = -1;
        for(int i = 0; i < contact.size(); i++) {
            if (contact.get(i).id.equals(person.id)){
                pos = i;
                break;
            }
        }
        if (pos > 0) {
            RawPerson rawPerson = contact.get(pos);
            rawPerson.name = person.name;
            rawPerson.avatar = person.avatar;
        }
    }

    @Override
    public List<Person> getAll() {
        List<Person> list = new ArrayList<>(contact.size());
        for(RawPerson person : contact) {
            list.add(ChatUtil.convert(person));
        }
        return list;
    }

    @Override
    public Person getDetail(String id) {
        int pos = -1;
        for(int i = 0; i < contact.size(); i++) {
            if (contact.get(i).id.equals(id)){
                pos = i;
                break;
            }
        }

        if (pos > 0) {
            return ChatUtil.convert(contact.get(pos));
        } else {
            return null;
        }
    }

    @Override
    public Person oneself() {
        return ChatUtil.convert(oneself);
    }

    // 伪造一些数据
    {
        RawPerson ntop = new RawPerson();
        ntop.avatar = R.drawable.flux_logo;
        ntop.id = UUID.randomUUID().toString();
        ntop.name = "ntop";

        RawPerson tom = new RawPerson();
        tom.avatar = R.drawable.flux_logo;
        tom.id = UUID.randomUUID().toString();
        tom.name = "Tom";

        RawPerson lilei = new RawPerson();
        lilei.avatar = R.drawable.flux_logo;
        lilei.id = UUID.randomUUID().toString();
        lilei.name = "Lilei";


        contact.add(ntop);
        contact.add(tom);
        contact.add(lilei);
    }
}
