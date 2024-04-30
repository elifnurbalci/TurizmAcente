package dao;

import entities.Season;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataAccess.DatabaseConnection;

public class SeasonDao {
    private Connection connection;

    public SeasonDao() {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<Season> findAll() {
        ArrayList<Season> seasons = new ArrayList<>();
        String sql = "SELECT * FROM public.season";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Season season = new Season();
                season.setId(rs.getInt("season_id"));
                season.setName(rs.getString("season_name"));
                season.setStartDate(rs.getDate("season_start_date").toLocalDate());
                season.setEndDate(rs.getDate("season_end_date").toLocalDate());
                seasons.add(season);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasons;
    }
    public ArrayList<Season> getSeasonsByHotel(int hotelId) {
        ArrayList<Season> seasons = new ArrayList<>();
        String sql = "SELECT s.* FROM public.season s " +
                "JOIN public.hotel_season hs ON s.season_id = hs.season_id " +
                "WHERE hs.hotel_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Season season = new Season();
                    season.setId(rs.getInt("season_id"));
                    season.setName(rs.getString("season_name"));
                    season.setStartDate(rs.getDate("season_start_date").toLocalDate());
                    season.setEndDate(rs.getDate("season_end_date").toLocalDate());
                    seasons.add(season);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasons;
    }


    public List<Season> getAllSeasons() {
        List<Season> seasons = new ArrayList<>();
        String sql = "SELECT * FROM public.season";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Season season = new Season();
                season.setId(rs.getInt("season_id"));
                season.setName(rs.getString("season_name"));
                season.setStartDate(rs.getDate("season_start_date").toLocalDate());
                season.setEndDate(rs.getDate("season_end_date").toLocalDate());
                seasons.add(season);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all seasons: " + e.getMessage());
        }
        return seasons;
    }

}
