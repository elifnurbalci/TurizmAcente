package dao;

import dataAccess.DatabaseConnection;
import entities.Hotel;
import entities.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoomDao {
    private Connection connection;

    public RoomDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    public boolean save(Room room) {
        String roomSql = "INSERT INTO public.room (room_type, room_stock, room_capacity, room_square_meter, television, minibar, game_console, cash_box, projection) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String joinSql = "INSERT INTO public.room_hotel (room_id, hotel_id) VALUES (?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement roomStmt = connection.prepareStatement(roomSql, PreparedStatement.RETURN_GENERATED_KEYS);
            roomStmt.setString(1, room.getType());
            roomStmt.setInt(2, room.getStock());
            roomStmt.setInt(3, room.getCapacity());
            roomStmt.setInt(4, room.getSquareMeter());
            roomStmt.setBoolean(5, room.isTelevision());
            roomStmt.setBoolean(6, room.isMinibar());
            roomStmt.setBoolean(7, room.isGameConsole());
            roomStmt.setBoolean(8, room.isCashBox());
            roomStmt.setBoolean(9, room.isProjection());
            int affectedRows = roomStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating room failed, no rows affected.");
            }
            ResultSet generatedKeys = roomStmt.getGeneratedKeys();
            int newRoomId = -1;
            if (generatedKeys.next()) {
                newRoomId = generatedKeys.getInt(1);
                PreparedStatement joinStmt = connection.prepareStatement(joinSql);
                joinStmt.setInt(1, newRoomId);
                joinStmt.setInt(2, room.getHotelId());
                joinStmt.executeUpdate();
                joinStmt.close();
            } else {
                throw new SQLException("Creating room failed, no ID obtained.");
            }
            roomStmt.close();
            connection.commit();
            return true;
        } catch (SQLException ex) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException e) {
                System.out.println("Rollback failed: " + e.getMessage());
            }
            System.out.println("Error: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Failed to reset auto-commit: " + e.getMessage());
            }
        }
    }


    public boolean update(Room room, Set<String> updatedFields) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE public.room SET ");
        List<Object> values = new ArrayList<>();

        if (updatedFields.contains("stock")) {
            sqlBuilder.append("room_stock = ?, ");
            values.add(room.getStock());
        }
        if (updatedFields.contains("capacity")) {
            sqlBuilder.append("room_capacity = ?, ");
            values.add(room.getCapacity());
        }
        if (updatedFields.contains("squareMeter")) {
            sqlBuilder.append("room_square_meter = ?, ");
            values.add(room.getSquareMeter());
        }
        if (updatedFields.contains("television")) {
            sqlBuilder.append("television = ?, ");
            values.add(room.isTelevision());
        }
        if (updatedFields.contains("minibar")) {
            sqlBuilder.append("minibar = ?, ");
            values.add(room.isMinibar());
        }
        if (updatedFields.contains("gameConsole")) {
            sqlBuilder.append("game_console = ?, ");
            values.add(room.isGameConsole());
        }
        if (updatedFields.contains("cashBox")) {
            sqlBuilder.append("cash_box = ?, ");
            values.add(room.isCashBox());
        }
        if (updatedFields.contains("projection")) {
            sqlBuilder.append("projection = ?, ");
            values.add(room.isProjection());
        }

        sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());

        sqlBuilder.append(" WHERE room_id = ?");
        values.add(room.getId());

        String sql = sqlBuilder.toString();
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object value : values) {
                preparedStatement.setObject(i++, value);
            }

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }


    public boolean delete(int roomId) {
        String deleteRoomHotelSql = "DELETE FROM public.room_hotel WHERE room_id = ?";
        String deleteRoomSql = "DELETE FROM public.room WHERE room_id = ?";
        try {
            connection.setAutoCommit(false);

            PreparedStatement deleteRoomHotelStmt = connection.prepareStatement(deleteRoomHotelSql);
            deleteRoomHotelStmt.setInt(1, roomId);
            deleteRoomHotelStmt.executeUpdate();
            deleteRoomHotelStmt.close();

            PreparedStatement deleteRoomStmt = connection.prepareStatement(deleteRoomSql);
            deleteRoomStmt.setInt(1, roomId);
            int affectedRows = deleteRoomStmt.executeUpdate();

            connection.commit();

            return affectedRows > 0;
        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Failed to reset auto-commit: " + ex.getMessage());
            }
        }
    }


    public List<Room> getRoomsByHotelAndType(int hotelId, String roomType) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.*, rh.hotel_id FROM public.room r " +
                "JOIN public.room_hotel rh ON r.room_id = rh.room_id " +
                "WHERE rh.hotel_id = ? AND r.room_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, roomType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rooms.add(extractRoomFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rooms by hotel and type: " + e.getMessage());
        }
        return rooms;
    }



    private Room extractRoomFromResultSet(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("room_id"));
        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("hotel_id"));
        room.setHotel(hotel);
        room.setType(rs.getString("room_type"));
        room.setStock(rs.getInt("room_stock"));
        room.setCapacity(rs.getInt("room_capacity"));
        room.setSquareMeter(rs.getInt("room_square_meter"));
        room.setTelevision(rs.getBoolean("television"));
        room.setMinibar(rs.getBoolean("minibar"));
        room.setGameConsole(rs.getBoolean("game_console"));
        room.setCashBox(rs.getBoolean("cash_box"));
        room.setProjection(rs.getBoolean("projection"));
        return room;
    }

    public List<Room> getRoomsByHotel(int hotelId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.*, rh.hotel_id FROM public.room r " +
                "JOIN public.room_hotel rh ON r.room_id = rh.room_id " +
                "WHERE rh.hotel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rooms.add(extractRoomFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rooms by hotel: " + e.getMessage());
        }
        return rooms;
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM public.room";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rooms.add(extractRoomFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all rooms: " + e.getMessage());
        }
        return rooms;
    }



}
