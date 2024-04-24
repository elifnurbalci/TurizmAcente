package dao;

import dataAccess.DatabaseConnection;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        this.connection = DatabaseConnection.getInstance();
    }
    public User findByLogin(String username, String password){
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_password = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                obj = new User();
                obj.setId(resultSet.getInt("user_id"));
                obj.setUsername(resultSet.getString("user_name"));
                obj.setPassword(resultSet.getString("user_password"));
                obj.setRole(resultSet.getString("user_role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;

    }
    public ArrayList<User> findAll(){
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM public.user";
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                User obj = new User();
                obj.setId(resultSet.getInt("user_id"));
                obj.setUsername(resultSet.getString("user_name"));
                obj.setPassword(resultSet.getString("user_password"));
                obj.setPassword(resultSet.getString("user_role"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
