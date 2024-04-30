package ui;

import business.*;
import dao.*;
import entities.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RoomPriceManagementView extends JFrame {
    private JPanel container;
    private JComboBox<String> cbHotel;
    private JComboBox<String> cbRoom;
    private JComboBox<String> cbSeason;
    private JComboBox<String> cbPension;
    private JTextField txtAdultPrice;
    private JTextField txtChildPrice;
    private JButton btnSave;
    private HotelManager hotelManager;
    private RoomManager roomManager;
    private RoomPriceManager roomPriceManager;
    private SeasonManager seasonManager;
    private PensionTypeManager pensionTypeManager;
    private List<Hotel> hotels;
    private HotelDao hotelDao;
    private RoomDao roomDao;
    private SeasonDao seasonDao;
    private PensionTypeDao pensionTypeDao;
    private RoomPriceDao roomPriceDao;

    public RoomPriceManagementView() {
        super("Room Price Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        container = new JPanel(new GridLayout(0, 2));

        hotelDao = new HotelDao();
        roomDao = new RoomDao();
        seasonDao = new SeasonDao();
        pensionTypeDao = new PensionTypeDao();
        roomPriceDao = new RoomPriceDao();

        hotelManager = new HotelManager(hotelDao);
        roomManager = new RoomManager(roomDao);
        roomPriceManager = new RoomPriceManager(roomPriceDao);
        seasonManager = new SeasonManager(seasonDao);
        pensionTypeManager = new PensionTypeManager(pensionTypeDao);

        addComponents();
        loadData();
        initListeners();

        getContentPane().add(container);
        setVisible(true);
    }



    private void addComponents() {
        cbHotel = new JComboBox<>();
        cbRoom = new JComboBox<>();
        cbSeason = new JComboBox<>();
        cbPension = new JComboBox<>();
        txtAdultPrice = new JTextField();
        txtChildPrice = new JTextField();
        btnSave = new JButton("Save");

        container.add(new JLabel("Hotel:"));
        container.add(cbHotel);
        container.add(new JLabel("Room:"));
        container.add(cbRoom);
        container.add(new JLabel("Season:"));
        container.add(cbSeason);
        container.add(new JLabel("Pension:"));
        container.add(cbPension);
        container.add(new JLabel("Adult Price:"));
        container.add(txtAdultPrice);
        container.add(new JLabel("Child Price:"));
        container.add(txtChildPrice);
        container.add(btnSave);
    }

    private void loadData() {
        hotels = hotelManager.findAll();
        hotels.forEach(hotel -> cbHotel.addItem(hotel.getName()));

        cbHotel.addActionListener(e -> {
            Hotel selectedHotel = getSelectedHotel();
            if (selectedHotel != null) {
                List<Room> rooms = roomManager.getRoomsByHotel(selectedHotel.getId());
                cbRoom.removeAllItems();
                rooms.forEach(room -> cbRoom.addItem(room.toString()));

                ArrayList<Season> seasons = seasonManager.getAllSeasons();
                cbSeason.removeAllItems();
                seasons.forEach(season -> cbSeason.addItem(season.getName()));

                pensionTypeManager.loadPensionTypes(cbPension);
            }
        });
    }

    private void initListeners() {
        btnSave.addActionListener(e -> {
            String hotelName = cbHotel.getSelectedItem().toString();
            String roomTypeName = cbRoom.getSelectedItem().toString();
            String seasonName = cbSeason.getSelectedItem().toString();
            String pensionTypeName = cbPension.getSelectedItem().toString();
            double adultPrice = 0.0;
            double childPrice = 0.0;

            try {
                adultPrice = Double.parseDouble(txtAdultPrice.getText());
                childPrice = Double.parseDouble(txtChildPrice.getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for prices.", "Invalid Price Format", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (saveRoomPrice(hotelName, roomTypeName, seasonName, pensionTypeName, adultPrice, childPrice)) {
                JOptionPane.showMessageDialog(null, "Room price saved successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to save room price. Please check the inputs and try again.", "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cbHotel.addActionListener(e -> {
            Hotel selectedHotel = getSelectedHotel();
            if (selectedHotel != null) {
                updateRoomData(selectedHotel.getId());
                updateSeasonData(selectedHotel.getId());
                updatePensionData(selectedHotel.getId());
            }
        });

    }

    private Hotel getSelectedHotel() {
        String selectedHotelName = (String) cbHotel.getSelectedItem();
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(selectedHotelName)) {
                return hotel;
            }
        }
        return null;
    }

    public boolean saveRoomPrice(String hotelName, String roomTypeName, String seasonName, String pensionTypeName, double adultPrice, double childPrice) {
        if (hotelDao == null || roomDao == null || seasonDao == null || pensionTypeDao == null) {
            System.out.println("DAOs not initialized");
            return false;
        }

        int hotelId = hotelDao.findHotelIdByName(hotelName);
        int roomId = roomDao.findRoomIdByName(roomTypeName);
        int seasonId = seasonDao.findSeasonIdByName(seasonName);
        int pensionTypeId = pensionTypeDao.findPensionTypeIdByName(pensionTypeName);

        if (hotelId == -1 || roomId == -1 || seasonId == -1 || pensionTypeId == -1) {
            JOptionPane.showMessageDialog(null, "One or more entities not found. Please verify the inputs.");
            return false;
        }

        RoomPrice roomPrice = new RoomPrice(roomId, hotelId, seasonId, pensionTypeId, adultPrice, childPrice);
        return roomPriceDao.save(roomPrice);
    }




    private void updateRoomData(int hotelId) {
        List<Room> rooms = roomManager.getRoomsByHotel(hotelId);
        cbRoom.removeAllItems();
        rooms.forEach(room -> cbRoom.addItem(room.getType() + " - Capacity: " + room.getCapacity()));
    }

    private void updateSeasonData(int hotelId) {
        ArrayList<Season> seasons = seasonManager.getSeasonsByHotel(hotelId);
        cbSeason.removeAllItems();
        seasons.forEach(season -> cbSeason.addItem(season.getName()));
    }

    private void updatePensionData(int hotelId) {
        List<PensionType> pensions = pensionTypeManager.getPensionsByHotel(hotelId);
        ArrayList<PensionType> pensionsArrayList = new ArrayList<>(pensions);
        cbPension.removeAllItems();
        pensionsArrayList.forEach(pension -> cbPension.addItem(pension.getName()));
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoomPriceManagementView::new);
    }
}
