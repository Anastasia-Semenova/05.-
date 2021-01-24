package ru.itis.javalab.repositories;

import ru.itis.javalab.models.User;

import java.util.List;
import java.util.UUID;

public interface UsersRepository extends CrudRepository<User> {
    List<User> findAllByAge(int age);
    List<User> findAllByUUID(String uuid);
    void saveUser(User user);
    List<User> getUserByLoginPassword(String login, String password);
}
