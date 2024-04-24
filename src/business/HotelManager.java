package business;

import dao.HotelDao;
import entities.Hotel;
import dataAccess.Helper;

import java.util.ArrayList;

public class HotelManager {
    private final HotelDao hotelDao = new HotelDao();

    public Hotel getByID(int id) {
        return this.hotelDao.getByHotelId(id);
    }

    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> hotelRowList = new ArrayList<>();
        for (Hotel hotel : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = hotel.getId();
            rowObject[i++] = hotel.getName();
            rowObject[i++] = hotel.getCity();
            rowObject[i++] = hotel.getRegion();
            rowObject[i++] = hotel.getAddress();
            rowObject[i++] = hotel.getEmail();
            rowObject[i++] = hotel.getPhone();
            rowObject[i++] = hotel.getStarRating();
            rowObject[i++] = hotel.getCarPark();
            rowObject[i++] = hotel.getWifi();
            rowObject[i++] = hotel.getPool();
            rowObject[i++] = hotel.getFitness();
            rowObject[i++] = hotel.getConcierge();
            rowObject[i++] = hotel.getSpa();
            rowObject[i++] = hotel.getRoomService();
            hotelRowList.add(rowObject);
        }
        return hotelRowList;
    }

    public boolean save(Hotel hotel) {
        if (this.hotelDao.getByHotelId(hotel.getId()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.hotelDao.save(hotel);
    }

    public boolean update(Hotel hotel) {
        if (this.hotelDao.getByHotelId(hotel.getId()) == null) {
            Helper.showMessage(hotel.getId() + " not found");
            return false;
        }
        return this.hotelDao.update(hotel);
    }

    public boolean delete(int id) {
        if (this.hotelDao.getByHotelId(id) == null) {
            Helper.showMessage("Hotel with ID " + id + " not found.");
            return false;
        }
        return this.hotelDao.delete(id);
    }
}
