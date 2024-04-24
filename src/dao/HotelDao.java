package dao;

import dataAccess.DatabaseConnection;
import entities.Hotel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDao {
    private Connection connection;

    public HotelDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    public Hotel getByHotelId(int id) {
        String sql = "SELECT * FROM public.hotel WHERE hotel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Hotel> findAll() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel ORDER BY hotel_id ASC";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                hotels.add(match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public boolean save(Hotel hotel) {
        String sql = "INSERT INTO public.hotel (hotel_name, hotel_city, hotel_region, hotel_address, hotel_email, hotel_phone, " +
                "hotel_star_rating, hotel_car_park, hotel_wifi, hotel_pool, hotel_fitness, hotel_concierge, hotel_spa, hotel_room_service) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, hotel.getName());
            preparedStatement.setString(2, hotel.getCity());
            preparedStatement.setString(3, hotel.getRegion());
            preparedStatement.setString(4, hotel.getAddress());
            preparedStatement.setString(5, hotel.getEmail());
            preparedStatement.setString(6, hotel.getPhone());
            preparedStatement.setString(7, hotel.getStarRating());
            preparedStatement.setBoolean(8, hotel.getCarPark());
            preparedStatement.setBoolean(9, hotel.getWifi());
            preparedStatement.setBoolean(10, hotel.getPool());
            preparedStatement.setBoolean(11, hotel.getFitness());
            preparedStatement.setBoolean(12, hotel.getConcierge());
            preparedStatement.setBoolean(13, hotel.getSpa());
            preparedStatement.setBoolean(14, hotel.getRoomService());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Hotel hotel) {
        String sql = "UPDATE public.hotel SET " +
                "hotel_name = ?, hotel_city = ?, hotel_region = ?, hotel_address = ?, hotel_email = ?, hotel_phone = ?, " +
                "hotel_star_rating = ?, hotel_car_park = ?, hotel_wifi = ?, hotel_pool = ?, hotel_fitness = ?, " +
                "hotel_concierge = ?, hotel_spa = ?, hotel_room_service = ? " +
                "WHERE hotel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, hotel.getName());
            preparedStatement.setString(2, hotel.getCity());
            preparedStatement.setString(3, hotel.getRegion());
            preparedStatement.setString(4, hotel.getAddress());
            preparedStatement.setString(5, hotel.getEmail());
            preparedStatement.setString(6, hotel.getPhone());
            preparedStatement.setString(7, hotel.getStarRating());
            preparedStatement.setBoolean(8, hotel.getCarPark());
            preparedStatement.setBoolean(9, hotel.getWifi());
            preparedStatement.setBoolean(10, hotel.getPool());
            preparedStatement.setBoolean(11, hotel.getFitness());
            preparedStatement.setBoolean(12, hotel.getConcierge());
            preparedStatement.setBoolean(13, hotel.getSpa());
            preparedStatement.setBoolean(14, hotel.getRoomService());
            preparedStatement.setInt(15, hotel.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM public.hotel WHERE hotel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Hotel match(ResultSet resultSet) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setId(resultSet.getInt("hotel_id"));
        hotel.setName(resultSet.getString("hotel_name"));
        hotel.setCity(resultSet.getString("hotel_city"));
        hotel.setRegion(resultSet.getString("hotel_region"));
        hotel.setAddress(resultSet.getString("hotel_address"));
        hotel.setEmail(resultSet.getString("hotel_email"));
        hotel.setPhone(resultSet.getString("hotel_phone"));
        hotel.setStarRating(resultSet.getString("hotel_star_rating"));
        hotel.setCarPark(resultSet.getBoolean("hotel_car_park"));
        hotel.setWifi(resultSet.getBoolean("hotel_wifi"));
        hotel.setPool(resultSet.getBoolean("hotel_pool"));
        hotel.setFitness(resultSet.getBoolean("hotel_fitness"));
        hotel.setConcierge(resultSet.getBoolean("hotel_concierge"));
        hotel.setSpa(resultSet.getBoolean("hotel_spa"));
        hotel.setRoomService(resultSet.getBoolean("hotel_room_service"));
        return hotel;
    }
}
