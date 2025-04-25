package com.cse5382.PhoneBook_Starter_Java_SpringBoot.Repository;

import com.cse5382.PhoneBook_Starter_Java_SpringBoot.Model.PhoneBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBookEntry, Long> {

    // Find a phone book entry by name
    Optional<PhoneBookEntry> findByName(String name);

    // Find a phone book entry by phone number
    Optional<PhoneBookEntry> findByPhoneNumber(String phoneNumber);
}