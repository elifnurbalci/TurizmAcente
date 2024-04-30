package dao;

import dataAccess.DatabaseConnection;
import entities.RoomPrice;
import java.sql.*;

public class RoomPriceDao {
    private Connection connection;

    public RoomPriceDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    public boolean save(RoomPrice roomPrice) {
        String sql = "INSERT INTO public.room_price (room_id, hotel_id, season_id, pension_type_id, adult_price, child_price) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            preparedStatement.setInt(1, roomPrice.getRoomId());
            preparedStatement.setInt(2, roomPrice.getHotelId());
            preparedStatement.setInt(3, roomPrice.getSeasonId());
            preparedStatement.setInt(4, roomPrice.getPensionTypeId());
            preparedStatement.setDouble(5, roomPrice.getAdultPrice());
            preparedStatement.setDouble(6, roomPrice.getChildPrice());

            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
            System.out.println("Save room price failed: " + e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.out.println("Auto-commit reset failed: " + e.getMessage());
            }
        }
    }



    public boolean update(RoomPrice roomPrice) {
        String sql = "UPDATE public.room_price SET adult_price = ?, child_price = ? WHERE room_id = ? AND hotel_id = ? AND season_id = ? AND pension_type_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, roomPrice.getAdultPrice());
            preparedStatement.setDouble(2, roomPrice.getChildPrice());
            preparedStatement.setInt(3, roomPrice.getRoomId());
            preparedStatement.setInt(4, roomPrice.getHotelId());
            preparedStatement.setInt(5, roomPrice.getSeasonId());
            preparedStatement.setInt(6, roomPrice.getPensionTypeId());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Update room price failed: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int roomId, int hotelId, int seasonId, int pensionTypeId) {
        String sql = "DELETE FROM public.room_price WHERE room_id = ? AND hotel_id = ? AND season_id = ? AND pension_type_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomId);
            preparedStatement.setInt(2, hotelId);
            preparedStatement.setInt(3, seasonId);
            preparedStatement.setInt(4, pensionTypeId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Delete room price failed: " + e.getMessage());
            return false;
        }
    }

}
