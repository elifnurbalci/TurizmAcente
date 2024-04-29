package ui;

import business.HotelManager;
import business.SeasonManager;
import dao.PensionTypeDao;
import dataAccess.Helper;
import entities.Hotel;
import entities.PensionType;
import entities.Season;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HotelView extends JFrame {
    private JPanel mainPanel;
    private JTextField fldHotelName, fldHotelEmail, fldHotelPhone, fldHotelAddress, fldHotelCity, fldHotelRegion;
    private JComboBox<String> comboStarRating;
    private List<JCheckBox> pensionTypeCheckboxes;
    private JCheckBox chkCarPark, chkWifi, chkPool, chkFitness, chkConcierge, chkSpa, chkRoomService;
    private JButton btnSave, btnCancel,btnDelete,btnClear;
    private JTable tblHotels;
    private HotelManager hotelManager;
    private PensionTypeDao pensionTypeDao;
    private List<Season> allSeasons;
    private JPanel seasonPanel;
    private List<JCheckBox> seasonCheckboxes;
    private JPanel pensionTypesPanel;
    private int selectedHotelId = -1;

    public HotelView() {
        hotelManager = new HotelManager();
        pensionTypeDao = new PensionTypeDao();
        initializeComponents();
        setupLayout();
        initListeners();
        updateTable();
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void initializeComponents() {
        mainPanel = new JPanel();
        fldHotelName = new JTextField(20);
        fldHotelEmail = new JTextField(20);
        fldHotelPhone = new JTextField(20);
        fldHotelAddress = new JTextField(20);
        fldHotelCity = new JTextField(20);
        fldHotelRegion = new JTextField(20);
        comboStarRating = new JComboBox<>(new String[]{"1 Star", "2 Stars", "3 Stars", "4 Stars", "5 Stars"});
        pensionTypeCheckboxes = new ArrayList<>();
        seasonCheckboxes = new ArrayList<>();

        chkCarPark = new JCheckBox("Car Park");
        chkWifi = new JCheckBox("Wi-Fi");
        chkPool = new JCheckBox("Swimming Pool");
        chkFitness = new JCheckBox("Fitness Center");
        chkConcierge = new JCheckBox("Concierge");
        chkSpa = new JCheckBox("Spa");
        chkRoomService = new JCheckBox("Room Service");

        btnSave = new JButton("SAVE");
        btnCancel = new JButton("CANCEL");
        btnDelete = new JButton("DELETE");
        btnClear = new JButton("CLEAR");

        tblHotels = new JTable();
        setupTable();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
    }



    private void setupLayout() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.add(new JLabel("Hotel Name:"));
        formPanel.add(fldHotelName);
        formPanel.add(new JLabel("Hotel City:"));
        formPanel.add(fldHotelCity);
        formPanel.add(new JLabel("Hotel Region:"));
        formPanel.add(fldHotelRegion);
        formPanel.add(new JLabel("Hotel Address:"));
        formPanel.add(fldHotelAddress);
        formPanel.add(new JLabel("Hotel Email:"));
        formPanel.add(fldHotelEmail);
        formPanel.add(new JLabel("Hotel Phone:"));
        formPanel.add(fldHotelPhone);
        formPanel.add(new JLabel("Star Rating:"));
        formPanel.add(comboStarRating);

        mainPanel.add(formPanel);

        JPanel amenitiesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        amenitiesPanel.add(chkCarPark);
        amenitiesPanel.add(chkWifi);
        amenitiesPanel.add(chkPool);
        amenitiesPanel.add(chkFitness);
        amenitiesPanel.add(chkConcierge);
        amenitiesPanel.add(chkSpa);
        amenitiesPanel.add(chkRoomService);
        mainPanel.add(amenitiesPanel);

        loadPensionTypes();
        loadSeasons();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnDelete);

        mainPanel.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(tblHotels);
        mainPanel.add(scrollPane);
    }


    private void setupTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "City", "Region", "Address", "Email", "Phone", "Star Rating","Car Park", "WIFI", "Pool", "Fitness Center", "Concierge", "Spa", "Room Service"}
        );
        tblHotels.setModel(model);
    }

    private void initListeners() {
        btnSave.addActionListener(e -> saveHotel());
        btnCancel.addActionListener(e -> dispose());
        btnDelete.addActionListener(e -> deleteHotel());
        btnClear.addActionListener(e -> clearForm());
        tblHotels.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblHotels.getSelectedRow() != -1) {
                fillFieldsWithSelectedHotel(tblHotels.getSelectedRow());
            } else {
                clearForm();
            }
        });
    }


    private void fillFieldsWithSelectedHotel(int selectedRow) {
        DefaultTableModel model = (DefaultTableModel) tblHotels.getModel();
        int hotelId = (int) model.getValueAt(selectedRow, 0);
        Hotel hotel = hotelManager.getByID(hotelId);

        if (hotel != null) {
            selectedHotelId = hotel.getId();

            fldHotelName.setText(hotel.getName());
            fldHotelCity.setText(hotel.getCity());
            fldHotelRegion.setText(hotel.getRegion());
            fldHotelAddress.setText(hotel.getAddress());
            fldHotelEmail.setText(hotel.getEmail());
            fldHotelPhone.setText(hotel.getPhone());
            comboStarRating.setSelectedItem(hotel.getStarRating());

            chkCarPark.setSelected(hotel.getCarPark());
            chkWifi.setSelected(hotel.getWifi());
            chkPool.setSelected(hotel.getPool());
            chkFitness.setSelected(hotel.getFitness());
            chkConcierge.setSelected(hotel.getConcierge());
            chkSpa.setSelected(hotel.getSpa());
            chkRoomService.setSelected(hotel.getRoomService());

            setPensionTypeCheckboxes(hotel.getId());
            setSeasonCheckboxes(hotel.getId());

            btnSave.setText("UPDATE");
            selectedHotelId = hotel.getId();
        } else {
            Helper.showMessage("Error: Hotel not found.");
            clearForm();
        }
    }



    private void setPensionTypeCheckboxes(int hotelId) {
        List<Integer> pensionTypeIds = hotelManager.getPensionTypeIdsForHotel(hotelId);
        for (JCheckBox checkBox : pensionTypeCheckboxes) {
            Integer pensionTypeId = Integer.parseInt(checkBox.getActionCommand());
            checkBox.setSelected(pensionTypeIds.contains(pensionTypeId));
        }
    }

    private void setSeasonCheckboxes(int hotelId) {
        List<Integer> seasonIds = hotelManager.getSeasonIdsForHotel(hotelId);
        for (JCheckBox checkBox : seasonCheckboxes) {
            Integer seasonId = Integer.parseInt(checkBox.getActionCommand());
            checkBox.setSelected(seasonIds.contains(seasonId));
        }
    }

    private void loadPensionTypes() {
        List<PensionType> pensionTypes = pensionTypeDao.getAllPensionTypes();
        pensionTypeCheckboxes.clear();

        if (pensionTypesPanel != null) {
            mainPanel.remove(pensionTypesPanel);
        }

        pensionTypesPanel = new JPanel(new FlowLayout());
        pensionTypesPanel.add(new JLabel("Pension Types"));
        JPanel checkboxPanel = new JPanel(new FlowLayout());
        for (PensionType type : pensionTypes) {
            JCheckBox checkBox = new JCheckBox(type.getName());
            checkBox.setActionCommand(String.valueOf(type.getId()));
            pensionTypeCheckboxes.add(checkBox);
            checkboxPanel.add(checkBox);
        }
        pensionTypesPanel.add(checkboxPanel);
        mainPanel.add(pensionTypesPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    private void saveHotel() {
        Hotel hotel = new Hotel();
        hotel.setId(selectedHotelId);
        hotel.setName(fldHotelName.getText());
        hotel.setCity(fldHotelCity.getText());
        hotel.setRegion(fldHotelRegion.getText());
        hotel.setAddress(fldHotelAddress.getText());
        hotel.setEmail(fldHotelEmail.getText());
        hotel.setPhone(fldHotelPhone.getText());
        hotel.setStarRating((String) comboStarRating.getSelectedItem());
        hotel.setCarPark(chkCarPark.isSelected());
        hotel.setWifi(chkWifi.isSelected());
        hotel.setPool(chkPool.isSelected());
        hotel.setFitness(chkFitness.isSelected());
        hotel.setConcierge(chkConcierge.isSelected());
        hotel.setSpa(chkSpa.isSelected());
        hotel.setRoomService(chkRoomService.isSelected());

        List<Integer> selectedSeasonIds = getSelectedSeasonIds();
        List<Integer> selectedPensionTypeIds = getSelectedPensionTypeIds();

        if (hotelManager.saveOrUpdate(hotel, selectedSeasonIds, selectedPensionTypeIds)) {
            JOptionPane.showMessageDialog(this, "Hotel saved or updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save or update the hotel.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        updateTable();
    }


    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) tblHotels.getModel();
        model.setRowCount(0);
        List<Hotel> hotels = hotelManager.findAll();
        for (Hotel hotel : hotels) {
            model.addRow(new Object[]{
                    hotel.getId(),
                    hotel.getName(),
                    hotel.getCity(),
                    hotel.getRegion(),
                    hotel.getAddress(),
                    hotel.getEmail(),
                    hotel.getPhone(),
                    hotel.getStarRating(),
                    hotel.getCarPark(),
                    hotel.getWifi(),
                    hotel.getPool(),
                    hotel.getFitness(),
                    hotel.getConcierge(),
                    hotel.getSpa(),
                    hotel.getRoomService()
            });
        }
    }
    private void loadSeasons() {
        allSeasons = new SeasonManager().getAllSeasons();
        seasonCheckboxes.clear();

        if (seasonPanel != null) {
            mainPanel.remove(seasonPanel);
        }

        seasonPanel = new JPanel(new FlowLayout());
        seasonPanel.add(new JLabel("Seasons"));
        for (Season season : allSeasons) {
            JCheckBox checkBox = new JCheckBox(season.getName());
            checkBox.setActionCommand(String.valueOf(season.getId()));
            seasonCheckboxes.add(checkBox);
            seasonPanel.add(checkBox);
        }
        mainPanel.add(seasonPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HotelView();
        });
    }
    private List<Integer> getSelectedSeasonIds() {
        List<Integer> selectedSeasonIds = new ArrayList<>();
        for (JCheckBox checkBox : seasonCheckboxes) {
            if (checkBox.isSelected()) {
                int seasonIndex = seasonCheckboxes.indexOf(checkBox);
                int seasonId = allSeasons.get(seasonIndex).getId();
                selectedSeasonIds.add(seasonId);
            }
        }
        return selectedSeasonIds;
    }

    private List<Integer> getSelectedPensionTypeIds() {
        List<Integer> selectedPensionTypeIds = new ArrayList<>();
        List<PensionType> allPensionTypes = pensionTypeDao.getAllPensionTypes();
        for (JCheckBox checkBox : pensionTypeCheckboxes) {
            if (checkBox.isSelected()) {
                int pensionTypeIndex = pensionTypeCheckboxes.indexOf(checkBox);
                int pensionTypeId = allPensionTypes.get(pensionTypeIndex).getId();
                selectedPensionTypeIds.add(pensionTypeId);
            }
        }
        return selectedPensionTypeIds;
    }
    private void deleteHotel() {
        int selectedRow = tblHotels.getSelectedRow();
        if (selectedRow != -1) {
            int hotelId = (int) tblHotels.getValueAt(selectedRow, 0);
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this hotel?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                if (hotelManager.delete(hotelId)) {
                    JOptionPane.showMessageDialog(this, "Hotel deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updateTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete the hotel.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a hotel to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void clearForm() {
        fldHotelName.setText("");
        fldHotelEmail.setText("");
        fldHotelPhone.setText("");
        fldHotelAddress.setText("");
        fldHotelCity.setText("");
        fldHotelRegion.setText("");
        comboStarRating.setSelectedIndex(0);  // Reset to default or first item

        chkCarPark.setSelected(false);
        chkWifi.setSelected(false);
        chkPool.setSelected(false);
        chkFitness.setSelected(false);
        chkConcierge.setSelected(false);
        chkSpa.setSelected(false);
        chkRoomService.setSelected(false);

        for (JCheckBox checkBox : pensionTypeCheckboxes) {
            checkBox.setSelected(false);
        }

        for (JCheckBox checkBox : seasonCheckboxes) {
            checkBox.setSelected(false);
        }

        btnSave.setText("SAVE");
        selectedHotelId = -1;  // Reset selected hotel ID
    }


}
