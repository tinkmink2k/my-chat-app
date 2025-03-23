package org.example.controller;

import org.example.dto.ContactDTO;
import org.example.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // tao nguoi dung moi
    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        ContactDTO createdContact = contactService.createContact(contactDTO);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }

    //cap nhat thong tin nguoi dung
    @PutMapping("/{contactId}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long contactId, @RequestBody ContactDTO contactDTO) {
        try {
            ContactDTO updatedContact = contactService.updateContact(contactId, contactDTO);
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Xoa nguoi dung theo ID
    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long contactId) {
        try{
            contactService.deleteContact(contactId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // tim nguoi dung theo id
    @GetMapping("/{contactId}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long contactId) {
        Optional<ContactDTO> contactDTO = contactService.getContactById(contactId);
        return contactDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //tim nguoi dung theo ten nguoi dung (username)
    @GetMapping("/username/{username}")
    public ResponseEntity<List<ContactDTO>> getContactByUsername(@PathVariable String username) {
        List<ContactDTO> contacts = contactService.getContactsByUsername(username);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    //tim nguoi dung theo id nguoi dung (userId)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ContactDTO>> getContactByUserId(@PathVariable Long userId) {
        List<ContactDTO> contacts = contactService.getContactsByUserId(userId);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}
