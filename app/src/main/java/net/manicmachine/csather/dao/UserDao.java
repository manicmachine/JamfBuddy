package net.manicmachine.csather.dao;

import net.manicmachine.csather.model.User;
import java.util.ArrayList;

public interface UserDao {

    User getUser(int userId);

    ArrayList<User> getAllUsers();

    void deleteUser(int userId);
    void addUser(User user);
    int userCount();
}
