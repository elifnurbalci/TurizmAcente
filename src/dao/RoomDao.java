package dao;

import dataAccess.DatabaseConnection;
import entities.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDao {
    private Connection connection;

    public RoomDao() {
        this.connection = DatabaseConnection.getInstance();
    }
    public Room getByRoomId(int id) {
        Room roomObj = null;
        String sql = "SELECT * FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) roomObj = this.match(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roomObj;
    }
    public ArrayList<Room> findAll() {
        ArrayList<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM public.room ORDER BY room_id ASC";
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                rooms.add(this.match(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rooms;
    }
    public boolean save(Room room) {
        String sql = "INSERT INTO public.room " +
                "(hotel_id, season_id, pension_id, room_type, room_stock, room_price_adult, room_price_child, room_capacity, room_square_meter, television, minibar, game_console, cash_box, projection) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, room.getHotelId());
            preparedStatement.setInt(2, room.getSeasonId());
            preparedStatement.setInt(3, room.getPensionId());
            preparedStatement.setString(4, room.getType());
            preparedStatement.setInt(5, room.getStock());
            preparedStatement.setDouble(6, room.getPriceAdult());
            preparedStatement.setDouble(7, room.getPriceChild());
            preparedStatement.setInt(8, room.getCapacity());
            preparedStatement.setInt(9, room.getSquareMeter());
            preparedStatement.setBoolean(10, room.isTelevision());
            preparedStatement.setBoolean(11, room.isMinibar());
            preparedStatement.setBoolean(12, room.isGameConsole());
            preparedStatement.setBoolean(13, room.isCashBox());
            preparedStatement.setBoolean(14, room.isProjection());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }



    public Room getByDetails(int hotelId, int seasonId, int pensionId, String type) {
        Room room = null;
        String sql = "SELECT * FROM public.room WHERE hotel_id = ? AND season_id = ? AND pension_id = ? AND room_type = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setInt(2, seasonId);
            preparedStatement.setInt(3, pensionId);
            preparedStatement.setString(4, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                room = match(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return room;
    }

    public boolean update(Room room) {
        String sql = "UPDATE public.room SET " +
                "room_type = ? , " +
                "room_stock = ? , " +
                "room_price_adult = ? , " +
                "room_price_child = ? , " +
                "room_capacity = ? , " +
                "room_square_meter = ? , " +
                "television = ? , " +
                "minibar = ? , " +
                "game_console = ? , " +
                "cash_box = ? , " +
                "projection = ? , " +
                "WHERE room_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,room.getId());
            preparedStatement.setString(2, room.getType());
            preparedStatement.setInt(3, room.getStock());
            preparedStatement.setInt(4,room.getCapacity());
            preparedStatement.setDouble(5,room.getSquareMeter());
            preparedStatement.setBoolean(6,room.isTelevision());
            preparedStatement.setBoolean(7,room.isMinibar());
            preparedStatement.setBoolean(8,room.isGameConsole());
            preparedStatement.setBoolean(9,room.isCashBox());
            preparedStatement.setBoolean(10,room.isProjection());
            preparedStatement.setInt(11,room.getId());


            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public boolean delete(int id) {
        String sql = "DELETE FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    private Room match(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setId(resultSet.getInt("room_id"));
        room.setType(resultSet.getString("room_type"));
        room.setStock(resultSet.getInt("room_stock"));
        room.setCapacity(resultSet.getInt("room_capacity"));
        room.setSquareMeter(resultSet.getInt("room_square_meter"));
        room.setTelevision(resultSet.getBoolean("television"));
        room.setMinibar(resultSet.getBoolean("minibar"));
        room.setGameConsole(resultSet.getBoolean("game_console"));
        room.setCashBox(resultSet.getBoolean("cash_box"));
        room.setProjection(resultSet.getBoolean("projection"));
        return room;
    }

}



