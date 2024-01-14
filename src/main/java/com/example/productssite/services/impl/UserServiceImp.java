package com.example.productssite.services.impl;

import com.example.productssite.entities.User;
import com.example.productssite.repositories.UserRepository;
import com.example.productssite.roles.Role;
import com.example.productssite.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRole().add(Role.USER_ROLE);
        userRepository.save(user);
    }

    @Override
    public boolean isEmailUnique(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void editWithoutPassword(User user) {
        User userFromDB = userRepository.findUserById(user.getId());

        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setCity(user.getCity());
        userFromDB.setPhoneNumber(user.getPhoneNumber());

        userRepository.save(userFromDB);
    }

    @Override
    public void changeActivity(Long id) {
        User user = userRepository.findUserById(id);

        user.setEnabled(!user.isEnabled());

        userRepository.save(user);
    }

    @Override
    public void changeRole(Long id) {
        User user = userRepository.findUserById(id);

        if (user.getRole().size() == 1)
            user.getRole().add(Role.ADMIN_ROLE);
        else
            user.getRole().remove(Role.ADMIN_ROLE);

        userRepository.save(user);
    }
}
