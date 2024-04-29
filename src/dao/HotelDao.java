package dao;

import dataAccess.DatabaseConnection;
import entities.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean save(Hotel hotel, List<Integer> seasonIds, List<Integer> pensionTypeIds) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getInstance();
            connection.setAutoCommit(false);

            String sqlHotel = "INSERT INTO public.hotel (hotel_name, hotel_city, hotel_region, hotel_address, hotel_email, hotel_phone, " +
                    "hotel_star_rating, hotel_car_park, hotel_wifi, hotel_pool, hotel_fitness, hotel_concierge, hotel_spa, hotel_room_service) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlHotel, Statement.RETURN_GENERATED_KEYS);
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
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating hotel failed, no rows affected.");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long hotelId = generatedKeys.getLong(1);

                String sqlSeason = "INSERT INTO public.hotel_season (hotel_id, season_id) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sqlSeason);
                for (int seasonId : seasonIds) {
                    preparedStatement.setLong(1, hotelId);
                    preparedStatement.setInt(2, seasonId);
                    preparedStatement.executeUpdate();
                }

                String sqlPension = "INSERT INTO public.hotel_pension_type (hotel_id, pension_type_id) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sqlPension);
                for (int pensionTypeId : pensionTypeIds) {
                    preparedStatement.setLong(1, hotelId);
                    preparedStatement.setInt(2, pensionTypeId);
                    preparedStatement.executeUpdate();
                }

                connection.commit();
                return true;
            } else {
                throw new SQLException("Creating hotel failed, no ID obtained.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public boolean update(Hotel hotel, List<Integer> newSeasonIds, List<Integer> newPensionTypeIds) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getInstance();
            connection.setAutoCommit(false);

            String sqlUpdateHotel = "UPDATE public.hotel SET " +
                    "hotel_name = ?, hotel_city = ?, hotel_region = ?, hotel_address = ?, hotel_email = ?, hotel_phone = ?, " +
                    "hotel_star_rating = ?, hotel_car_park = ?, hotel_wifi = ?, hotel_pool = ?, hotel_fitness = ?, " +
                    "hotel_concierge = ?, hotel_spa = ?, hotel_room_service = ? " +
                    "WHERE hotel_id = ?";
            preparedStatement = connection.prepareStatement(sqlUpdateHotel);
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
            preparedStatement.setInt(15, hotel.getId()); // Hotel ID

            preparedStatement.executeUpdate();

            String sqlUpdateSeason = "DELETE FROM public.hotel_season WHERE hotel_id = ?";
            preparedStatement = connection.prepareStatement(sqlUpdateSeason);
            preparedStatement.setInt(1, hotel.getId());
            preparedStatement.executeUpdate();

            String sqlInsertSeason = "INSERT INTO public.hotel_season (hotel_id, season_id) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sqlInsertSeason);
            for (int seasonId : newSeasonIds) {
                preparedStatement.setInt(1, hotel.getId());
                preparedStatement.setInt(2, seasonId);
                preparedStatement.executeUpdate();
            }

            String sqlUpdatePension = "DELETE FROM public.hotel_pension_type WHERE hotel_id = ?";
            preparedStatement = connection.prepareStatement(sqlUpdatePension);
            preparedStatement.setInt(1, hotel.getId());
            preparedStatement.executeUpdate();

            String sqlInsertPension = "INSERT INTO public.hotel_pension_type (hotel_id, pension_type_id) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sqlInsertPension);
            for (int pensionTypeId : newPensionTypeIds) {
                preparedStatement.setInt(1, hotel.getId());
                preparedStatement.setInt(2, pensionTypeId);
                preparedStatement.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public boolean delete(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getInstance();
            connection.setAutoCommit(false);

            String sqlDeletePension = "DELETE FROM public.hotel_pension_type WHERE hotel_id = ?";
            preparedStatement = connection.prepareStatement(sqlDeletePension);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            String sqlDeleteSeason = "DELETE FROM public.hotel_season WHERE hotel_id = ?";
            preparedStatement = connection.prepareStatement(sqlDeleteSeason);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            String sqlDeleteHotel = "DELETE FROM public.hotel WHERE hotel_id = ?";
            preparedStatement = connection.prepareStatement(sqlDeleteHotel);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            connection.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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


    public List<Integer> getPensionTypeIdsForHotel(int hotelId) {
        List<Integer> pensionTypeIds = new ArrayList<>();
        String sql = "SELECT pension_type_id FROM public.hotel_pension_type WHERE hotel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pensionTypeIds.add(resultSet.getInt("pension_type_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensionTypeIds;
    }

    public List<Integer> getSeasonIdsForHotel(int hotelId) {
        List<Integer> seasonIds = new ArrayList<>();
        String sql = "SELECT season_id FROM public.hotel_season WHERE hotel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                seasonIds.add(resultSet.getInt("season_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasonIds;
    }



}
