package business;

import dataAccess.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class PensionTypeManager {
    private Connection connection;

    public PensionTypeManager() {
        this.connection = DatabaseConnection.getInstance();
    }

    public void loadPensionTypes(JComboBox<String> comboBox) {
        String sql = "SELECT pension_type_name FROM public.pension_type ORDER BY pension_type_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            comboBox.removeAllItems();
            while (resultSet.next()) {
                comboBox.addItem(resultSet.getString("pension_type_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
