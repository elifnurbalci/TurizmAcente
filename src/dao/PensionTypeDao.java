package dao;

import entities.PensionType;
import java.sql.*;
import java.util.ArrayList;
import dataAccess.DatabaseConnection;

public class PensionTypeDao {
    private Connection connection;

    public PensionTypeDao() {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<PensionType> findAll() {
        ArrayList<PensionType> pensionTypes = new ArrayList<>();
        String sql = "SELECT * FROM pension_type";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PensionType pensionType = new PensionType();
                pensionType.setId(rs.getInt("pension_type_id"));
                pensionType.setName(rs.getString("pension_type_name"));
                pensionTypes.add(pensionType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensionTypes;
    }
}
