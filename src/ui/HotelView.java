package ui;

import business.HotelManager;
import business.PensionTypeManager;
import business.RoomManager;
import business.SeasonManager;
import dao.PensionTypeDao;
import dao.SeasonDao;
import entities.Hotel;
import entities.PensionType;
import entities.Room;
import entities.Season;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class HotelView extends JFrame {
    private JPanel container, w_top,w_bottom, tab_hotel_add, w_hotel_add_top,w_hotel_add_bottom,w_hotel_add_mid,tab_add_room_price,w_add_room_price,w_season_winter,tab_hotel_list;
    private JTabbedPane tab_pnl_main;
    private JTextField fld_hotel_name, fld_hotel_mail, fld_hotel_phone, fld_hotel_address,textField1,textField2,fld_hotel_city,fld_hotel_region;
    private JCheckBox cbox_carpark, cbox_concierge, cbox_wifi, cbox_spa, cbox_swimming_pool, cbox_room_service, cbox_fitness,doubleRoom2PeopleCheckBox,suiteRoom4PeopleCheckBox,juniorSuiteRoom3CheckBox,singleRoom1PersonCheckBox,bedAndBreakfastCheckBox,halfBoardCheckBox,fullBoardCheckBox,allInclusiveCheckBox,cbox_tv,cbox_minibar,cbox_game_console,cbox_cash_box,cbox_projection;
    private JComboBox<String> cbox_single_room_quota,cbox_double_room_quota,cbox_junior_suite_room_quota,cbox_suite_room_quota,cbox_select_room_type,combo_star_rate,comboBox1,cbox_select_season,cbox_select_hotel;
    private JTable tbl_hotel_list;
    private JButton btnAddOrUpdate, btn_add_hotel, btn_cancel_hotel_add, btn_cancel_hotel_list,btn_add_room_cancel,btn_add_room_price,btnSaveRoom;
    private JLabel lbl_hotel_name,lbl_title,lbl_hotel_mail,lbl_hotel_phone,lbl_hotel_address,lbl_star_rate,lbl_room_title,lbl_quota,lbl_,lbl_select_season,lbl_select_room_type,lbl_select_pension,lbl_adult_price,lbl_children_price,lbl_select_hotel;
    private HotelManager hotelManager;
    private Hotel currentHotel;
    private RoomManager roomManager;
    private SeasonManager seasonManager;
    private PensionTypeManager pensionTypeManager;

    // New UI components for the Room Management tab
    private JComboBox<Hotel> hotelComboBox;
    private JComboBox<Season> seasonComboBox;
    private JComboBox<PensionType> pensionComboBox;
    private JComboBox<String> roomTypeComboBox;
    private JTextField adultPriceField;
    private JTextField childPriceField;
    private JCheckBox tvCheckBox;
    private JCheckBox minibarCheckBox;
    private JTextField capacityField,stockField;
    private JTextField squareMeterField;
    private JCheckBox gameConsoleCheckBox;
    private JCheckBox cashBoxCheckBox;
    private JCheckBox projectionCheckBox;

    public HotelView() {
        hotelManager = new HotelManager();
        roomManager = new RoomManager();
        seasonManager = new SeasonManager();
        pensionTypeManager = new PensionTypeManager();

        initializeComponents();
        initializeListeners();
        loadHotelTable();
        setFrameProperties();
    }
    private void initializeComponents() {
        container = new JPanel(new BorderLayout());
        tab_pnl_main = new JTabbedPane();

        JPanel hotelInfoPanel = createHotelInfoPanel();
        tab_pnl_main.addTab("Hotel Info", hotelInfoPanel);

        JPanel hotelListPanel = createHotelListPanel();
        tab_pnl_main.addTab("Hotel List", hotelListPanel);

        JPanel roomPanel = createRoomPanel();
        tab_pnl_main.addTab("Room Management", roomPanel);

        container.add(tab_pnl_main);
        setContentPane(container);
    }

    private JPanel createHotelInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        fld_hotel_name = new JTextField();
        fld_hotel_mail = new JTextField();
        fld_hotel_phone = new JTextField();
        fld_hotel_address = new JTextField();
        fld_hotel_city = new JTextField();
        fld_hotel_region = new JTextField();
        combo_star_rate = new JComboBox<>(new String[]{"1 Star", "2 Stars", "3 Stars", "4 Stars", "5 Stars"});

        cbox_carpark = new JCheckBox("Car Park");
        cbox_concierge = new JCheckBox("Concierge");
        cbox_wifi = new JCheckBox("Wi-Fi");
        cbox_spa = new JCheckBox("Spa");
        cbox_swimming_pool = new JCheckBox("Swimming Pool");
        cbox_room_service = new JCheckBox("Room Service");
        cbox_fitness = new JCheckBox("Fitness Center");

        panel.add(new JLabel("Hotel Name:"));
        panel.add(fld_hotel_name);
        panel.add(new JLabel("City:"));
        panel.add(fld_hotel_city);
        panel.add(new JLabel("Region:"));
        panel.add(fld_hotel_region);
        panel.add(new JLabel("Address:"));
        panel.add(fld_hotel_address);
        panel.add(new JLabel("Email:"));
        panel.add(fld_hotel_mail);
        panel.add(new JLabel("Phone:"));
        panel.add(fld_hotel_phone);
        panel.add(new JLabel("Star Rating:"));
        panel.add(combo_star_rate);
        panel.add(cbox_carpark);
        panel.add(cbox_concierge);
        panel.add(cbox_wifi);
        panel.add(cbox_spa);
        panel.add(cbox_swimming_pool);
        panel.add(cbox_room_service);
        panel.add(cbox_fitness);

        btnAddOrUpdate = new JButton("Save");
        btn_cancel_hotel_add = new JButton("Cancel");
        panel.add(btnAddOrUpdate);
        panel.add(btn_cancel_hotel_add);

        return panel;
    }

    private JPanel createHotelListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tbl_hotel_list = new JTable(new DefaultTableModel(
                new Object[][] {},
                new String[] {"Hotel ID", "Hotel Name", "City", "Region", "Address", "Email", "Phone", "Star Rate", "Car Park", "WIFI", "Pool", "Fitness", "Concierge", "SPA", "Room Service"}
        ));
        tbl_hotel_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tbl_hotel_list);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnDeleteHotel = new JButton("Delete Hotel");
        JButton btnUpdateHotel = new JButton("Update Hotel");
        buttonPanel.add(btnUpdateHotel);
        buttonPanel.add(btnDeleteHotel);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        btnUpdateHotel.addActionListener(e -> updateSelectedHotel());
        btnDeleteHotel.addActionListener(e -> deleteSelectedHotel());

        return panel;
    }

    private void updateSelectedHotel() {
        int row = tbl_hotel_list.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hotel to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int hotelId = (int) tbl_hotel_list.getValueAt(row, 0);
        currentHotel = hotelManager.getByID(hotelId);
        if (currentHotel != null) {
            loadHotelData(currentHotel);
            btnAddOrUpdate.setText("Update");
            tab_pnl_main.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Hotel not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedHotel() {
        int row = tbl_hotel_list.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hotel to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int hotelId = (int) tbl_hotel_list.getValueAt(row, 0);
        if (hotelManager.delete(hotelId)) {
            JOptionPane.showMessageDialog(this, "Hotel successfully deleted.");
            loadHotelTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete hotel.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addOrUpdateHotel() {
        if (validateFields()) {
            Hotel hotel = (currentHotel == null) ? new Hotel() : currentHotel;
            populateHotelData(hotel);
            boolean success = (currentHotel == null) ? hotelManager.save(hotel) : hotelManager.update(hotel);
            if (success) {
                JOptionPane.showMessageDialog(this, (currentHotel == null) ? "Hotel added successfully!" : "Hotel updated successfully!");
                loadHotelTable();
                clearForm();
                currentHotel = null;  // Reset the current hotel
                btnAddOrUpdate.setText("Save");  // Reset button text to "Save"
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save hotel.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateFields() {
        if (fld_hotel_name.getText().trim().isEmpty() || fld_hotel_mail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void populateHotelData(Hotel hotel) {
        hotel.setName(fld_hotel_name.getText().trim());
        hotel.setEmail(fld_hotel_mail.getText().trim());
        hotel.setPhone(fld_hotel_phone.getText().trim());
        hotel.setAddress(fld_hotel_address.getText().trim());
        hotel.setCity(fld_hotel_city.getText().trim());
        hotel.setRegion(fld_hotel_region.getText().trim());
        hotel.setStarRating((String) combo_star_rate.getSelectedItem());
        hotel.setCarPark(cbox_carpark.isSelected());
        hotel.setWifi(cbox_wifi.isSelected());
        hotel.setPool(cbox_swimming_pool.isSelected());
        hotel.setFitness(cbox_fitness.isSelected());
        hotel.setConcierge(cbox_concierge.isSelected());
        hotel.setSpa(cbox_spa.isSelected());
        hotel.setRoomService(cbox_room_service.isSelected());
    }

    private void loadHotelData(Hotel hotel) {
        fld_hotel_name.setText(hotel.getName());
        fld_hotel_mail.setText(hotel.getEmail());
        fld_hotel_phone.setText(hotel.getPhone());
        fld_hotel_address.setText(hotel.getAddress());
        fld_hotel_city.setText(hotel.getCity());
        fld_hotel_region.setText(hotel.getRegion());
        combo_star_rate.setSelectedItem(hotel.getStarRating());
        cbox_carpark.setSelected(hotel.getCarPark());
        cbox_wifi.setSelected(hotel.getWifi());
        cbox_swimming_pool.setSelected(hotel.getPool());
        cbox_fitness.setSelected(hotel.getFitness());
        cbox_concierge.setSelected(hotel.getConcierge());
        cbox_spa.setSelected(hotel.getSpa());
        cbox_room_service.setSelected(hotel.getRoomService());
    }

    private void initializeListeners() {
        btn_cancel_hotel_add.addActionListener(e -> clearForm());
        btn_cancel_hotel_list.addActionListener(e -> dispose());
        btnAddOrUpdate.addActionListener(e -> addOrUpdateHotel());
    }

    private void clearForm() {
        fld_hotel_name.setText("");
        fld_hotel_mail.setText("");
        fld_hotel_phone.setText("");
        fld_hotel_address.setText("");
        fld_hotel_city.setText("");
        fld_hotel_region.setText("");
        combo_star_rate.setSelectedIndex(0);
        cbox_carpark.setSelected(false);
        cbox_concierge.setSelected(false);
        cbox_wifi.setSelected(false);
        cbox_spa.setSelected(false);
        cbox_swimming_pool.setSelected(false);
        cbox_room_service.setSelected(false);
        cbox_fitness.setSelected(false);
        btnAddOrUpdate.setText("Save"); // Reset button text to "Save"
        currentHotel = null; // Clear the current hotel object
    }

    private void loadHotelTable() {
        ArrayList<Object[]> hotelList = hotelManager.getForTable(15);
        DefaultTableModel model = (DefaultTableModel) tbl_hotel_list.getModel();
        model.setRowCount(0);
        for (Object[] row : hotelList) {
            model.addRow(row);
        }
    }

    private void setFrameProperties() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }
    private void populateRoomTypes(JComboBox<String> comboBox) {
        String[] roomTypes = {"single-room", "double-room", "junior suite room", "suite room"};
        for (String type : roomTypes) {
            comboBox.addItem(type);
        }
    }
    private JPanel createRoomPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        hotelComboBox = new JComboBox<>();
        seasonComboBox = new JComboBox<>();
        pensionComboBox = new JComboBox<>();
        roomTypeComboBox = new JComboBox<>(new String[]{"single-room", "double-room", "junior suite room", "suite room"});
        capacityField = new JTextField();
        squareMeterField = new JTextField();
        stockField = new JTextField(); // Stock field initialize edildi
        gameConsoleCheckBox = new JCheckBox("Has Game Console");
        cashBoxCheckBox = new JCheckBox("Has Cash Box");
        projectionCheckBox = new JCheckBox("Has Projection");


        loadHotelData();
        loadSeasonData();
        loadPensionTypeData();
        setComboBoxRenderer();

        adultPriceField = new JTextField();
        childPriceField = new JTextField();
        tvCheckBox = new JCheckBox("Has TV");
        minibarCheckBox = new JCheckBox("Has Minibar");
        btnSaveRoom = new JButton("Save Room");

        addRoomManagementComponents(panel);

        btnSaveRoom.addActionListener(e -> saveRoom());
        panel.add(btnSaveRoom);
        return panel;
    }

    private void loadHotelData() {
        List<Hotel> hotels = hotelManager.findAll();
        hotelComboBox.setModel(new DefaultComboBoxModel<>(hotels.toArray(new Hotel[0])));
    }

    private void loadSeasonData() {
        List<Season> seasons = seasonManager.getAllSeasons();
        seasonComboBox.setModel(new DefaultComboBoxModel<>(seasons.toArray(new Season[0])));
    }

    private void loadPensionTypeData() {
        List<PensionType> pensionTypes = pensionTypeManager.getAllPensionTypes();
        pensionComboBox.setModel(new DefaultComboBoxModel<>(pensionTypes.toArray(new PensionType[0])));
    }

    private void setComboBoxRenderer() {
        hotelComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Hotel) {
                    setText(((Hotel) value).getName());
                }
                return this;
            }
        });

        seasonComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Season) {
                    setText(((Season) value).getName());
                }
                return this;
            }
        });

        pensionComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof PensionType) {
                    setText(((PensionType) value).getName());
                }
                return this;
            }
        });
    }

    private void addRoomManagementComponents(JPanel panel) {
        panel.add(new JLabel("Select Hotel:"));
        panel.add(hotelComboBox);
        panel.add(new JLabel("Select Pension Type:"));
        panel.add(pensionComboBox);
        panel.add(new JLabel("Select Season:"));
        panel.add(seasonComboBox);
        panel.add(new JLabel("Room Type:"));
        panel.add(roomTypeComboBox);
        panel.add(new JLabel("Room Stock:"));
        panel.add(stockField);
        panel.add(new JLabel("Adult Price:"));
        panel.add(adultPriceField);
        panel.add(new JLabel("Child Price:"));
        panel.add(childPriceField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Square Meter:"));
        panel.add(squareMeterField);
        panel.add(tvCheckBox);
        panel.add(minibarCheckBox);
        panel.add(projectionCheckBox);
        panel.add(gameConsoleCheckBox);
        panel.add(cashBoxCheckBox);
        panel.add(btnSaveRoom);
    }

    private void saveRoom() {
        try {
            Room room = new Room();
            room.setHotelId(((Hotel) hotelComboBox.getSelectedItem()).getId());
            room.setSeasonId(((Season) seasonComboBox.getSelectedItem()).getId());
            room.setPensionId(((PensionType) pensionComboBox.getSelectedItem()).getId());
            room.setType((String) roomTypeComboBox.getSelectedItem());
            room.setPriceAdult(Double.parseDouble(adultPriceField.getText().trim()));
            room.setPriceChild(Double.parseDouble(childPriceField.getText().trim()));
            room.setTelevision(tvCheckBox.isSelected());
            room.setMinibar(minibarCheckBox.isSelected());
            room.setStock(Integer.parseInt(stockField.getText().trim())); // Kullanıcıdan stok sayısını al

            if (roomManager.save(room)) {
                JOptionPane.showMessageDialog(this, "Room saved successfully!");
                clearRoomForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save room.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format in price or stock fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving room: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void clearRoomForm() {
        adultPriceField.setText("");
        childPriceField.setText("");
        capacityField.setText("");
        squareMeterField.setText("");
        stockField.setText("");
        tvCheckBox.setSelected(false);
        minibarCheckBox.setSelected(false);
        projectionCheckBox.setSelected(false);
        gameConsoleCheckBox.setSelected(false);
        cashBoxCheckBox.setSelected(false);
        roomTypeComboBox.setSelectedIndex(0);

    }


    public static void main(String[] args) {
        new HotelView();
    }
}