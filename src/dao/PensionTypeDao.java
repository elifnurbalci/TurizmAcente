package dao;

import entities.PensionType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.DatabaseConnection;

public class PensionTypeDao {
    private Connection connection;

    public PensionTypeDao() {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<PensionType> getAllPensionTypes() {
        ArrayList<PensionType> pensionTypes = new ArrayList<>();
        String sql = "SELECT * FROM public.pension_type";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PensionType pensionType = new PensionType();
                pensionType.setId(rs.getInt("pension_type_id"));
                pensionType.setName(rs.getString("pension_type_name"));
                pensionTypes.add(pensionType);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all pension types: " + e.getMessage());
        }
        return pensionTypes;
    }
}
