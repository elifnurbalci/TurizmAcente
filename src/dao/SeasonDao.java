package dao;

import entities.Season;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
}
