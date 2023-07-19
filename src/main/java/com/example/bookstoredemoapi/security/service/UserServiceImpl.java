package com.example.bookstoredemoapi.security.service;

import com.example.bookstoredemoapi.dao.UserRepository;
import com.example.bookstoredemoapi.security.model.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String userName) {
        return userRepository.findUserByUserName(userName).orElse(null);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
