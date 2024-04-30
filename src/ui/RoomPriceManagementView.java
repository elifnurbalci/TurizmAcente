package ui;

import business.*;
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

    public RoomPriceManagementView() {
        super("Room Price Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        container = new JPanel(new GridLayout(0, 2));
        hotelManager = new HotelManager();
        roomManager = new RoomManager();
        roomPriceManager = new RoomPriceManager();
        seasonManager = new SeasonManager();
        pensionTypeManager = new PensionTypeManager();
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
        btnSave.addActionListener(e -> saveRoomPrice());
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

    private void saveRoomPrice() {
        String selectedHotel = (String) cbHotel.getSelectedItem();
        String selectedRoom = (String) cbRoom.getSelectedItem();
        String selectedSeason = (String) cbSeason.getSelectedItem();
        String selectedPension = (String) cbPension.getSelectedItem();
        String adultPriceText = txtAdultPrice.getText();
        String childPriceText = txtChildPrice.getText();

        if (selectedHotel == null || selectedRoom == null || selectedSeason == null || selectedPension == null || adultPriceText.isEmpty() || childPriceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double adultPrice, childPrice;
        try {
            adultPrice = Double.parseDouble(adultPriceText);
            childPrice = Double.parseDouble(childPriceText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int roomId = roomManager.findRoomIdByName(selectedRoom);
        if (roomId == -1) {
            JOptionPane.showMessageDialog(this, "Selected room is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int hotelId = hotelManager.findHotelIdByName(selectedHotel);
        int seasonId = seasonManager.findSeasonIdByName(selectedSeason);
        int pensionTypeId = pensionTypeManager.findPensionTypeIdByName(selectedPension);

        RoomPrice roomPrice = new RoomPrice(roomId, hotelId, seasonId, pensionTypeId, adultPrice, childPrice);

        boolean success = roomPriceManager.save(roomPrice);
        if (success) {
            JOptionPane.showMessageDialog(this, "Price saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save price.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
