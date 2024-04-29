package business;

import dao.RoomDao;
import entities.Room;
import java.util.List;
import java.util.Set;

public class RoomManager {
    private RoomDao roomDao;

    public RoomManager() {
        this.roomDao = new RoomDao();
    }

    public boolean save(Room room) {
        return roomDao.save(room);
    }


    public boolean update(Room room, Set<String> updatedFields) {
        return roomDao.update(room, updatedFields);
    }


    public boolean delete(int roomId) {
        return roomDao.delete(roomId);
    }

    public List<Room> getRoomsByHotelAndType(int hotelId, String roomType) {
        return roomDao.getRoomsByHotelAndType(hotelId, roomType);
    }
}

