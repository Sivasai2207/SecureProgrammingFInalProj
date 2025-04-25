package com.cse5382.PhoneBook_Starter_Java_SpringBoot.Service;

import com.cse5382.PhoneBook_Starter_Java_SpringBoot.Model.AuditLog;
import com.cse5382.PhoneBook_Starter_Java_SpringBoot.Model.PhoneBookEntry;
import com.cse5382.PhoneBook_Starter_Java_SpringBoot.Repository.AuditLogRepository;
import com.cse5382.PhoneBook_Starter_Java_SpringBoot.Repository.PhoneBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneBookServiceImpl implements PhoneBookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneBookServiceImpl.class);
    private static final String DB_URL = "jdbc:sqlite:phonebook.db";

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Log every operation
    private void logAudit(String operation, String target) {
        AuditLog log = new AuditLog(operation, target, LocalDateTime.now());
        auditLogRepository.save(log);
    }

    @Override
    public List<PhoneBookEntry> list() {
        List<PhoneBookEntry> entries = new ArrayList<>();
        try {
            // Get the list of entries from the database
            entries = phoneBookRepository.findAll();

            // Log this operation
            logAudit("LIST", "Fetched all entries");

        } catch (Exception e) {
            LOGGER.error("Error listing entries", e);
        }

        return entries;
    }

    @Override
    public void add(PhoneBookEntry entry) {
        try {
            // Save the entry in the database
            phoneBookRepository.save(entry);

            // Log the add operation
            logAudit("ADD", "Added entry: " + entry.getName());
            LOGGER.info("Added new entry: {}", entry);

        } catch (Exception e) {
            LOGGER.error("Error adding entry", e);
        }
    }

    @Override
    public Optional<PhoneBookEntry> getById(Long id) {
        try {
            Optional<PhoneBookEntry> entry = phoneBookRepository.findById(id);
            if (entry.isPresent()) {
                logAudit("GET_BY_ID", "Fetched entry with ID: " + id);
                return entry;
            } else {
                LOGGER.warn("No entry found with ID: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            LOGGER.error("Error fetching by ID", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Long id, PhoneBookEntry entry) {
        try {
            Optional<PhoneBookEntry> existingEntry = phoneBookRepository.findById(id);
            if (existingEntry.isPresent()) {
                PhoneBookEntry updatedEntry = existingEntry.get();
                updatedEntry.setName(entry.getName());
                updatedEntry.setPhoneNumber(entry.getPhoneNumber());
                phoneBookRepository.save(updatedEntry);

                logAudit("UPDATE", "Updated entry with ID: " + id);
                LOGGER.info("Updated entry with ID: {}", id);
                return true;
            } else {
                LOGGER.warn("No entry found with ID: {}", id);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error updating entry", e);
            return false;
        }
    }

    @Override
    public boolean deleteByName(String name) {
        try {
            Optional<PhoneBookEntry> entry = phoneBookRepository.findByName(name);
            if (entry.isPresent()) {
                phoneBookRepository.delete(entry.get());

                // Log the delete operation
                logAudit("DELETE", "Deleted entry with name: " + name);
                LOGGER.info("Deleted entry with name: {}", name);
                return true;
            } else {
                LOGGER.warn("No entry found with name: {}", name);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting by name", e);
            return false;
        }
    }

    @Override
    public boolean deleteByNumber(String number) {
        try {
            Optional<PhoneBookEntry> entry = phoneBookRepository.findByPhoneNumber(number);
            if (entry.isPresent()) {
                phoneBookRepository.delete(entry.get());

                // Log the delete operation
                logAudit("DELETE", "Deleted entry with phone number: " + number);
                LOGGER.info("Deleted entry with phone number: {}", number);
                return true;
            } else {
                LOGGER.warn("No entry found with phone number: {}", number);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting by number", e);
            return false;
        }
    }
}
