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

    public RoomManager(RoomDao roomDao) {
        this.roomDao = roomDao;
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
    public List<Room> getRoomsByHotel(int hotelId) {
        return roomDao.getRoomsByHotel(hotelId);
    }

    public int findRoomIdByName(String roomType) {
        List<Room> rooms = roomDao.getAllRooms();
        System.out.println("Loaded room types:");
        for (Room room : rooms) {
            System.out.println(room.getType());
            if (room.getType().equalsIgnoreCase(roomType)) {
                return room.getId();
            }
        }
        System.out.println("No matching room found for type: " + roomType);
        return -1;
    }


}

