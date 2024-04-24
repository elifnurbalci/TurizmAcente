package business;

import dao.RoomDao;
import entities.Room;
import java.util.ArrayList;

public class RoomManager {
    private RoomDao roomDao;

    public RoomManager() {
        this.roomDao = new RoomDao();
    }

    public Room getByRoomId(int id) {
        return roomDao.getByRoomId(id);
    }

    public ArrayList<Room> findAll() {
        return roomDao.findAll();
    }

    public boolean save(Room room) {
        Room existingRoom = roomDao.getByDetails(room.getHotelId(), room.getSeasonId(), room.getPensionId(), room.getType());

        if (existingRoom != null) {
            int newCapacity = existingRoom.getCapacity() + room.getCapacity();
            existingRoom.setCapacity(newCapacity);
            existingRoom.setStock(existingRoom.getStock() + room.getStock());
            return roomDao.update(existingRoom);
        } else {
            room.setStock(room.getStock());
            return roomDao.save(room);
        }
    }



    public boolean update(Room room) {
        return roomDao.update(room);
    }

    public boolean delete(int id) {
        return roomDao.delete(id);
    }

}
