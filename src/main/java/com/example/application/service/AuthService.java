package com.example.application.service;


import com.example.application.models.Employee;
import com.example.application.models.User;
import com.example.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user != null) {
            System.out.println("Input Username: " + username);
            System.out.println("Input Password: " + password);
            System.out.println("User Retrieved: " + user.getUsername() + " " + user.getPassword());

            if (user.getPassword().equals(password)) {
                if(user.isAdmin()){
                    return 1;
                }
                else return 2;
            }
        }

        return -1;
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void addUser(User u ){
        userRepository.save(u);
    }



}
