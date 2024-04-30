package business;

import entities.Season;
import dao.SeasonDao;
import java.util.ArrayList;
import java.util.List;

public class SeasonManager {
    private SeasonDao seasonDao;

    public SeasonManager() {
        seasonDao = new SeasonDao();
    }

    public SeasonManager(SeasonDao seasonDao) {
        this.seasonDao = seasonDao;
    }

    public ArrayList<Season> getAllSeasons() {
        return seasonDao.findAll();
    }
    public int findSeasonIdByName(String seasonName) {
        List<Season> seasons = seasonDao.getAllSeasons();
        for (Season season : seasons) {
            if (season.getName().equals(seasonName)) {
                return season.getId();
            }
        }
        return -1;
    }

    public ArrayList<Season> getSeasonsByHotel(int hotelId) {
        return seasonDao.getSeasonsByHotel(hotelId);
    }
}
