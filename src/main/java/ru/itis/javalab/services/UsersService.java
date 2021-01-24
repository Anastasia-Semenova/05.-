package ru.itis.javalab.services;

import ru.itis.javalab.models.User;

import java.util.List;

public interface UsersService {
    List<User> getAllUsers();
    void saveUser(User user);
    List<User> getUserByLoginPassword(String login, String password);
    List<User> getUsersByUuid(String uuid);
}
