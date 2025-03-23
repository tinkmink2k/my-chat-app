package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.ContactDTO;
import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = convertDTOtoEntity(contactDTO);
        contact = contactRepository.save(contact);
        return convertEntitytoDTO(contact);
    }

    @Transactional
    public ContactDTO updateContact(Long contactId, ContactDTO contactDTO) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new RuntimeException("Contact not found"));
        contact.setUsername(contactDTO.getUserName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        contact = contactRepository.save(contact);
        return convertEntitytoDTO(contact);
    }

    @Transactional
    public void deleteContact(Long contactId) {
        contactRepository.findById(contactId).orElseThrow(() -> new RuntimeException("Contact not found"));
        contactRepository.deleteById(contactId);
    }

    public Optional<ContactDTO> getContactById(Long contactId) {
        Optional<Contact> contact = contactRepository.findById(contactId);
        return contact.map(this::convertEntitytoDTO);
    }

    public List<ContactDTO> getContactsByUserId(Long userId) {
        List<Contact> contacts = contactRepository.findByUser_UserId(userId);
        return contacts.stream().map(this::convertEntitytoDTO).collect(Collectors.toList());
    }

    public List<ContactDTO> getContactsByUsername(String username) {
        List<Contact> contacts = contactRepository.findByUsername(username);
        if (contacts.isEmpty()) {
            throw new RuntimeException("Contact not found");
        }
        return contacts.stream().map(this::convertEntitytoDTO).collect(Collectors.toList());
    }

    private Contact convertDTOtoEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setContactId(contactDTO.getContactId());
        contact.setUsername(contactDTO.getUserName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        return contact;
    }

    private ContactDTO convertEntitytoDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactId(contact.getContactId());
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setUserName(contact.getUsername());
        return contactDTO;
    }
}
