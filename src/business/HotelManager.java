package business;

import dao.HotelDao;
import entities.Hotel;
import dataAccess.Helper;

import java.util.ArrayList;
import java.util.List;

public class HotelManager {
    private final HotelDao hotelDao = new HotelDao();

    public HotelManager(HotelDao hotelDao) {
    }

    public HotelManager() {

    }

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

    public boolean save(Hotel hotel, List<Integer> seasonId, List<Integer> pensionTypeId) {
        if (hotel.getName() == null || hotel.getName().trim().isEmpty()) {
            Helper.showMessage("Error: Hotel name cannot be empty.");
            return false;
        }
        if (this.hotelDao.getByHotelId(hotel.getId()) != null) {
            Helper.showMessage("Error: Hotel already exists.");
            return false;
        }
        return this.hotelDao.save(hotel, seasonId, pensionTypeId);
    }

    public boolean update(Hotel hotel, List<Integer> seasonId, List<Integer> pensionTypeId) {
        if (hotel.getName() == null || hotel.getName().trim().isEmpty()) {
            Helper.showMessage("Error: Hotel name cannot be empty.");
            return false;
        }
        if (this.hotelDao.getByHotelId(hotel.getId()) == null) {
            Helper.showMessage("Hotel with ID " + hotel.getId() + " not found");
            return false;
        }
        return this.hotelDao.update(hotel, seasonId, pensionTypeId);
    }


    public boolean delete(int id) {
        if (this.hotelDao.getByHotelId(id) == null) {
            Helper.showMessage("Hotel with ID " + id + " not found.");
            return false;
        }
        return this.hotelDao.delete(id);
    }

    public boolean saveOrUpdate(Hotel hotel, List<Integer> seasonIds, List<Integer> pensionTypeIds) {
        if (hotel.getName() == null || hotel.getName().trim().isEmpty()) {
            Helper.showMessage("Error: Hotel name cannot be empty.");
            return false;
        }

        if (hotel.getId() > 0) {
            return update(hotel, seasonIds, pensionTypeIds);
        } else {
            return save(hotel, seasonIds, pensionTypeIds);
        }
    }


    public List<Integer> getPensionTypeIdsForHotel(int hotelId) {
        return hotelDao.getPensionTypeIdsForHotel(hotelId);
    }

    public List<Integer> getSeasonIdsForHotel(int hotelId) {
        return hotelDao.getSeasonIdsForHotel(hotelId);
    }

    public int findHotelIdByName(String hotelName) {
        return hotelDao.findHotelIdByName(hotelName);
    }

}
