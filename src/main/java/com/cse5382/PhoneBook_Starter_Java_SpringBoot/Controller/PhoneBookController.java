package com.cse5382.PhoneBook_Starter_Java_SpringBoot.Controller;

import com.cse5382.PhoneBook_Starter_Java_SpringBoot.Model.PhoneBookEntry;
import com.cse5382.PhoneBook_Starter_Java_SpringBoot.Service.PhoneBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PhoneBook")
public class PhoneBookController {

    private final PhoneBookService phoneBookService;

    @Autowired
    public PhoneBookController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('READER','WRITER')")
    public ResponseEntity<List<PhoneBookEntry>> listEntries() {
        List<PhoneBookEntry> entries = phoneBookService.list();
        if (entries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('WRITER')")
    public ResponseEntity<String> addEntry(@Valid @RequestBody PhoneBookEntry entry) {
        phoneBookService.add(entry);
        return ResponseEntity.ok("Entry added successfully");
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('WRITER')")
    public ResponseEntity<String> updateEntry(@PathVariable Long id,
                                              @Valid @RequestBody PhoneBookEntry entry) {
        return phoneBookService.update(id, entry)
             ? ResponseEntity.ok("Entry updated successfully")
             : ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body("No entry found with the given ID");
    }

    @PutMapping("/delete-by-name")
    @PreAuthorize("hasRole('WRITER')")
    public ResponseEntity<String> deleteByName(@RequestParam String name) {
        return phoneBookService.deleteByName(name)
             ? ResponseEntity.ok("Entries deleted by name")
             : ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body("No entries found with the given name");
    }

    @PutMapping("/delete-by-number")
    @PreAuthorize("hasRole('WRITER')")
    public ResponseEntity<String> deleteByNumber(@RequestParam String number) {
        return phoneBookService.deleteByNumber(number)
             ? ResponseEntity.ok("Entries deleted by number")
             : ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body("No entries found with the given number");
    }
}
