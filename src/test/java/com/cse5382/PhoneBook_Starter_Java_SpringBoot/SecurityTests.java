package com.cse5382.PhoneBook_Starter_Java_SpringBoot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUnauthorizedAccessToAdd() throws Exception {
        mockMvc.perform(post("/phonebook/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "Alice Smith",
                          "phoneNumber": "+1(703)111-1234"
                        }
                        """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "WRITER")
    public void testWriterCanAddEntry() throws Exception {
        mockMvc.perform(post("/phonebook/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "Alice Smith",
                          "phoneNumber": "+1(703)111-1234"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "READER")
    public void testReaderCannotAddEntry() throws Exception {
        mockMvc.perform(post("/phonebook/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "Bob Reader",
                          "phoneNumber": "011 1 703 123 4567"
                        }
                        """))
                .andExpect(status().isForbidden());
    }

}
