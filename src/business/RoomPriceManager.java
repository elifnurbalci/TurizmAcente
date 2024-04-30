package business;

import dao.RoomPriceDao;
import entities.RoomPrice;

public class RoomPriceManager {
    private RoomPriceDao roomPriceDao;

    public RoomPriceManager() {
        this.roomPriceDao = new RoomPriceDao();
    }

    public RoomPriceManager(RoomPriceDao roomPriceDao) {
        this.roomPriceDao = roomPriceDao;
    }

    public boolean save(RoomPrice roomPrice) {
        return roomPriceDao.save(roomPrice);
    }

    public boolean update(RoomPrice roomPrice) {
        return roomPriceDao.update(roomPrice);
    }

    public boolean delete(int roomId, int hotelId, int seasonId, int pensionTypeId) {
        return roomPriceDao.delete(roomId, hotelId, seasonId, pensionTypeId);
    }

}
