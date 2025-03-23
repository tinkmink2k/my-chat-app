package org.example.service;

import org.example.dto.UserDTO;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //tim nguoi dung theo ID va tra ve UserDTO
    public Optional<UserDTO> getUserById(Long userId){
        return userRepository.findById(userId).map(this::convertEntityToDTO);
    }

    //tim nguoi dung theo email
    public Optional<UserDTO> getUserByEmail(String email){
        return userRepository.findByEmail(email).map(this::convertEntityToDTO);
    }

    //tim nguoi dung theo phone
    public Optional<UserDTO> getUserByPhone(String phone){
        return userRepository.findByPhone(phone).map(this::convertEntityToDTO);
    }

    //tim nguoi dung theo username
    public Optional<UserDTO> getUserByUsername(String username){
        return userRepository.findByUsername(username).map(this::convertEntityToDTO);
    }

    //lay danh sach nguoi dung va chuyen sang dang UserDTO
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    //chuyen doi tu user entity sang userDTO
    public UserDTO convertEntityToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone()
        );
    }

    //chuyen doi tu userDto sang user(entity)
    public User convertDTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        return user;
    }

    //tao nguoi dung moi
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertDTOToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertEntityToDTO(savedUser);
    }

    //cap nhat thong tin cua nguoi dung moi
    public UserDTO updateUser(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getUserId())){
            User user = convertDTOToEntity(userDTO);
            User updatedUser = userRepository.save(user);
            return convertEntityToDTO(updatedUser);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    //xoa ng dung theo ID
    public void deleteUser(UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    //tim kiem nguoi dung theo username
    public Optional<UserDTO> findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(this::convertEntityToDTO);
    }

    //tim kiem nguoi dung theo id
    public Optional<UserDTO> findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(this::convertEntityToDTO);
    }

    //tim kiem nguoi dung theo phone
    public Optional<UserDTO> findUserByPhone(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        return user.map(this::convertEntityToDTO);
    }

    //tim kiem nguoi dung theo email
    public Optional<UserDTO> findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(this::convertEntityToDTO);
    }
}
