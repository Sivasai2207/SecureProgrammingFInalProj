package com.cse5382.PhoneBook_Starter_Java_SpringBoot.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "phonebook")
public class PhoneBookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name cannot be blank.")
    @Pattern(
    regexp =
        "^(?=.{1,40}$)(?!.*(['\"<>;]{2,}|\\b(select|drop|insert|delete|update|script)\\b))" +
        "(" +
        "[A-Za-z][a-zA-Z'’`\\-]+(?:,\\s?[A-Za-z][a-zA-Z'’`\\.\\-]+(?:\\s[A-Za-z]\\.){0,2})?" +
        "|" +
        "[A-Za-z][a-zA-Z'’`\\-]+(?:\\s[A-Za-z][a-zA-Z'’`\\-]+){0,2}" +
        ")$",
    message = "Invalid name format."
    )
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Phone number cannot be blank.")
    @Pattern(
    regexp = "^(?:\\d{5}" +                      // pure 5-digit
            "|(?:(?:\\+?\\d{1,3}|011|00)[-. ]?)?" + // optional +CC / 011 / 00 prefix
                "(?:" +
                "(?:\\(\\d{2,3}\\)|\\d{2,3})[-. ]?\\d{3}[-. ]?\\d{4}" + // (AAA)BBB-CCCC or AAA-BBB-CCCC
                "|\\d{4}[-. ]?\\d{4}"    +            // 4-4 (e.g. Danish)
                "|\\d{5}[-. ]?\\d{5}"    +            // 5-5
                "|\\d{3}[-. ]?\\d{4}"                 // 3-4 (e.g. 123-4567)
                +
                ")" +
            ")$",
    message = "Invalid phone number format."
    )
    @Column(unique = true)
    private String phoneNumber;

    // Default constructor
    public PhoneBookEntry() { }

    // Convenience constructor
    public PhoneBookEntry(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name != null ? name.trim() : null;
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name != null ? name.trim() : null;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public String getPhoneNumber() {
        return phoneNumber != null ? phoneNumber.trim() : null;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;
    }

    @Override
    public String toString() {
        return "PhoneBookEntry{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               '}';
    }
}
