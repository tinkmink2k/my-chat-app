package org.example.repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    Tìm người dùng theo email
        Optional<User> findByEmail(String email);

//    Tìm người dùng theo số điện thoại
        Optional<User> findByPhone(String phone);

//    Tìm người dùng theo tên người dùng (username)
        Optional<User> findByUsername(String username);

//     Kiểm tra sự tồn tại của người dùng theo email
        boolean existsByEmail(String email);

//     Kiểm tra sự tồn tại của người dùng theo tên người dùng
        boolean existsByUsername(String username);

//      Kiểm tra sự tồn tại của người dùng theo số điện thoại
        boolean existsByPhone(String phone);
}
