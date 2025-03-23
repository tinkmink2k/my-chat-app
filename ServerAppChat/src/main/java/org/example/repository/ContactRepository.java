package org.example.repository;

import org.example.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    //tim kiem cac lien he theo user_id
    List<Contact> findByUser_UserId(Long userId);

    //tim kiem lien he theo username
    List<Contact> findByUsername(String username);

    //Tim kiem lien he theo so dien thoai
    List<Contact> findByPhone(String phone);

    //Tim kiem lien he theo email
    List<Contact> findByEmail(String email);

    List<Contact> findByUserId(Long userId);

}
