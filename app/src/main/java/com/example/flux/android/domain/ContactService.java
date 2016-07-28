package com.example.flux.android.domain;

import com.example.flux.android.domain.model.RawPerson;
import com.example.flux.android.model.Person;

import java.util.List;

/**
 * Created by ntop on 16/7/29.
 */
public interface ContactService {
    public void create(String name);
    public void delete(String id);
    public void update(RawPerson person);
    public List<Person> getAll();
    public Person getDetail(String id);
    public Person oneself();
}
