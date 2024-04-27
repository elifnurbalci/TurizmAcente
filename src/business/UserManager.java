package business;

import dao.UserDao;
import entities.User;

import java.util.List;

public class UserManager {
    private final UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }

    public User findByLogin(String username, String password) {
        // Directly use the password for login
        return userDao.findByLogin(username, password);
    }
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public boolean addUser(String username, String password, String role) {
        // Store the password directly without hashing
        return userDao.addUser(new User(username, password, role));
    }

    public boolean updateUser(String username, String password, String role) {
        // Update user info directly with the provided password
        return userDao.updateUser(new User(username, password, role));
    }

    public boolean deleteUser(String username) {
        // Delete user by username
        return userDao.deleteUser(username);
    }
}
