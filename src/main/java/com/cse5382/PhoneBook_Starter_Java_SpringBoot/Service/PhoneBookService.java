package com.cse5382.PhoneBook_Starter_Java_SpringBoot.Service;



import com.cse5382.PhoneBook_Starter_Java_SpringBoot.Model.PhoneBookEntry;

import java.util.List;
import java.util.Optional;

public interface PhoneBookService {

    List<PhoneBookEntry> list();

    void add(PhoneBookEntry phoneBookEntry);

    Optional<PhoneBookEntry> getById(Long id);

    boolean update(Long id, PhoneBookEntry phoneBookEntry);

    boolean deleteByName(String name);

    boolean deleteByNumber(String phoneNumber);
}
