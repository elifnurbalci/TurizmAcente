package business;

import entities.Season;
import dao.SeasonDao;
import java.util.ArrayList;

public class SeasonManager {
    private SeasonDao seasonDao;

    public SeasonManager() {
        seasonDao = new SeasonDao();
    }

    public ArrayList<Season> getAllSeasons() {
        return seasonDao.findAll();
    }
}
