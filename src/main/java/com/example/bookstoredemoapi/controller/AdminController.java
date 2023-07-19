package com.example.bookstoredemoapi.controller;

import com.example.bookstoredemoapi.dao.UserRepository;
import com.example.bookstoredemoapi.security.jwt.JwtProvider;
import com.example.bookstoredemoapi.security.model.Role;
import com.example.bookstoredemoapi.security.model.User;
import com.example.bookstoredemoapi.security.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    record RequestUser(String name, @JsonProperty("username")String userName,String password){}

    record ResponseUser(int id,@JsonProperty("username") String userName){

    }
    @PostMapping("/user/login")
    public ResponseEntity<?> login(Principal principal){
        System.out.println(principal);
        if(principal==null){
            return ResponseEntity.ok(principal);
        }
        UsernamePasswordAuthenticationToken token=(UsernamePasswordAuthenticationToken) principal;
        User user=userService.findByUsername(token.getName());
        user.setToken(jwtProvider.generateToken(token));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody RequestUser requestUser){
        if(userService.findByUsername(requestUser.userName())!=null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        var savedUser=userService.saveUser(new User(requestUser.name(),
                requestUser.userName(),
                requestUser.password(),
                Role.USER));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseUser(savedUser.getId(),
                        savedUser.getUserName()));
    }

}
