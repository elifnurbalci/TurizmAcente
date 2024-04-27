package dao;

import dataAccess.DatabaseConnection;
import dataAccess.Helper;
import entities.User;
import ui.AdminUserView;
import ui.EmployeeView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {
    private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    public UserDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    public User findByLogin(String username, String password) {
        User user = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_password = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);  // Directly use the password
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUsername(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("user_password"));
                user.setRole(resultSet.getString("user_role"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding user by login", e);
        }
        return user;
    }

    public ArrayList<User> findAll() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM public.user";
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("user_password"));
                user.setRole(resultSet.getString("user_role"));
                userList.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all users", e);
        }
        return userList;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO public.user (user_name, user_password, user_role) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());  // Directly use the password
            preparedStatement.setString(3, user.getRole());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding user", e);
            return false;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE public.user SET user_password = ?, user_role = ? WHERE user_name = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getPassword());  // Directly use the password
            preparedStatement.setString(2, user.getRole());
            preparedStatement.setString(3, user.getUsername());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating user", e);
            return false;
        }
    }


    public boolean deleteUser(String username) {
        String sql = "DELETE FROM public.user WHERE user_name = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting user", e);
            return false;
        }
    }


}
