package dao;

import dataAccess.DatabaseConnection;
import entities.HotelPensionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HotelPensionTypeDao {
    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(HotelPensionTypeDao.class.getName());

    public HotelPensionTypeDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    public boolean addHotelPensionType(HotelPensionType hotelPensionType) {
        String sql = "INSERT INTO public.hotel_pension_type (hotel_id, pension_type_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, hotelPensionType.getHotelId());
            preparedStatement.setInt(2, hotelPensionType.getPensionTypeId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding hotel pension type", e);
            return false;
        }
    }
}
