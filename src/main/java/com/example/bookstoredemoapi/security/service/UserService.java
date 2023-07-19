package com.example.bookstoredemoapi.security.service;

import com.example.bookstoredemoapi.security.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    User saveUser(User user);

    User findByUsername(String userName);

     List<User> findAllUser();
}
