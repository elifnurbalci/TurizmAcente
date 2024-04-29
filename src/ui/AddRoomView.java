package ui;

import business.HotelManager;
import business.RoomManager;
import entities.Hotel;
import entities.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddRoomView extends JFrame {
    private final RoomManager roomManager;
    private final HotelManager hotelManager;

    private final JComboBox<Hotel> cbHotel;
    private final JComboBox<String> cbRoomType;
    private final JTextField tfStock, tfPriceAdult, tfPriceChild, tfCapacity, tfSquareMeter;
    private final JCheckBox cbTelevision, cbMinibar, cbGameConsole, cbSafeBox, cbProjection;
    private final JButton btnSave, btnListRooms, btnDelete, btnUpdate;
    private final JTable tblRooms;
    private JPanel container;

    public AddRoomView() {
        roomManager = new RoomManager();
        hotelManager = new HotelManager();
        cbHotel = new JComboBox<>();
        cbRoomType = new JComboBox<>(new String[]{"Single", "Double", "Junior Suite", "Suite"});
        tfStock = new JTextField(10);
        tfPriceAdult = new JTextField(10);
        tfPriceChild = new JTextField(10);
        tfCapacity = new JTextField(10);
        tfSquareMeter = new JTextField(10);
        cbTelevision = new JCheckBox("Television");
        cbMinibar = new JCheckBox("Minibar");
        cbGameConsole = new JCheckBox("Game Console");
        cbSafeBox = new JCheckBox("Safe Box");
        cbProjection = new JCheckBox("Projection");
        btnSave = new JButton("Save");
        btnListRooms = new JButton("List Rooms");
        btnDelete = new JButton("Delete");
        btnUpdate = new JButton("Update");
        tblRooms = new JTable();

        initializeComponents();
        setupFormPanel();
        setupTable();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        loadHotels();

        cbHotel.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                clearRoomList();
            }
        });

        btnSave.addActionListener(e -> saveRoom());
        btnListRooms.addActionListener(e -> listRooms());
        btnDelete.addActionListener(e -> deleteRoom());
        btnUpdate.addActionListener(e -> updateRoom());
    }

    private void setupFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.add(new JLabel("Hotel:"));
        formPanel.add(cbHotel);
        formPanel.add(new JLabel("Room Type:"));
        formPanel.add(cbRoomType);
        addTextFieldsToPanel(formPanel);
        addCheckBoxesToPanel(formPanel);
        formPanel.add(btnSave);
        formPanel.add(btnListRooms);
        formPanel.add(btnDelete);
        add(formPanel, BorderLayout.NORTH);
    }

    private void addTextFieldsToPanel(JPanel panel) {
        panel.add(new JLabel("Stock:"));
        panel.add(tfStock);
        panel.add(new JLabel("Adult Price:"));
        panel.add(tfPriceAdult);
        panel.add(new JLabel("Child Price:"));
        panel.add(tfPriceChild);
        panel.add(new JLabel("Capacity:"));
        panel.add(tfCapacity);
        panel.add(new JLabel("Square Meters:"));
        panel.add(tfSquareMeter);
    }

    private void addCheckBoxesToPanel(JPanel panel) {
        panel.add(cbTelevision);
        panel.add(cbMinibar);
        panel.add(cbGameConsole);
        panel.add(cbSafeBox);
        panel.add(cbProjection);
    }

    private void setupTable() {
        JScrollPane scrollPane = new JScrollPane(tblRooms);
        add(scrollPane, BorderLayout.CENTER);

        tblRooms.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblRooms.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) tblRooms.getModel();
                    Room selectedRoom = new Room();
                    selectedRoom.setId((int) model.getValueAt(selectedRow, 0));
                    selectedRoom.setType((String) model.getValueAt(selectedRow, 1));
                    selectedRoom.setStock((int) model.getValueAt(selectedRow, 2));
                    selectedRoom.setPriceAdult((double) model.getValueAt(selectedRow, 3));
                    selectedRoom.setPriceChild((double) model.getValueAt(selectedRow, 4));
                    selectedRoom.setCapacity((int) model.getValueAt(selectedRow, 5));
                    selectedRoom.setSquareMeter((int) model.getValueAt(selectedRow, 6));
                    selectedRoom.setTelevision((boolean) model.getValueAt(selectedRow, 7));
                    selectedRoom.setMinibar((boolean) model.getValueAt(selectedRow, 8));
                    selectedRoom.setGameConsole((boolean) model.getValueAt(selectedRow, 9));
                    selectedRoom.setCashBox((boolean) model.getValueAt(selectedRow, 10));
                    selectedRoom.setProjection((boolean) model.getValueAt(selectedRow, 11));

                    tfStock.setText(String.valueOf(selectedRoom.getStock()));
                    tfPriceAdult.setText(String.valueOf(selectedRoom.getPriceAdult()));
                    tfPriceChild.setText(String.valueOf(selectedRoom.getPriceChild()));
                    tfCapacity.setText(String.valueOf(selectedRoom.getCapacity()));
                    tfSquareMeter.setText(String.valueOf(selectedRoom.getSquareMeter()));
                    cbTelevision.setSelected(selectedRoom.isTelevision());
                    cbMinibar.setSelected(selectedRoom.isMinibar());
                    cbGameConsole.setSelected(selectedRoom.isGameConsole());
                    cbSafeBox.setSelected(selectedRoom.isCashBox());
                    cbProjection.setSelected(selectedRoom.isProjection());
                    btnSave.setText("Update");
                }
            }
        });
    }

    private void loadHotels() {
        List<Hotel> hotels = hotelManager.findAll();
        for (Hotel hotel : hotels) {
            cbHotel.addItem(hotel);
        }
    }

    private void clearRoomList() {
        DefaultTableModel model = (DefaultTableModel) tblRooms.getModel();
        model.setRowCount(0);
    }

    private void saveRoom() {
        try {
            Room room = new Room();
            Hotel selectedHotel = (Hotel) cbHotel.getSelectedItem();
            room.setHotelId(selectedHotel.getId());
            room.setType((String) cbRoomType.getSelectedItem());
            room.setStock(Integer.parseInt(tfStock.getText()));
            room.setPriceAdult(Double.parseDouble(tfPriceAdult.getText()));
            room.setPriceChild(Double.parseDouble(tfPriceChild.getText()));
            room.setCapacity(Integer.parseInt(tfCapacity.getText()));
            room.setSquareMeter(Integer.parseInt(tfSquareMeter.getText()));
            room.setTelevision(cbTelevision.isSelected());
            room.setMinibar(cbMinibar.isSelected());
            room.setGameConsole(cbGameConsole.isSelected());
            room.setCashBox(cbSafeBox.isSelected());
            room.setProjection(cbProjection.isSelected());

            if (roomManager.save(room)) {
                JOptionPane.showMessageDialog(this, "Room saved successfully!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save the room.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please check your number fields for correct values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listRooms() {
        Hotel selectedHotel = (Hotel) cbHotel.getSelectedItem();
        String selectedRoomType = (String) cbRoomType.getSelectedItem();
        if (selectedHotel == null || selectedRoomType == null || selectedRoomType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a hotel and room type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Room> rooms = roomManager.getRoomsByHotelAndType(selectedHotel.getId(), selectedRoomType);

        DefaultTableModel model = (DefaultTableModel) tblRooms.getModel();
        model.setRowCount(0);

        String[] columnNames = {"ID", "Type", "Stock", "Price Adult", "Price Child", "Capacity", "Square Meter", "Television", "Minibar", "Game Console", "Safe Box", "Projection"};
        model.setColumnIdentifiers(columnNames);

        for (Room room : rooms) {
            Object[] row = new Object[]{
                    room.getId(),
                    room.getType(),
                    room.getStock(),
                    room.getPriceAdult(),
                    room.getPriceChild(),
                    room.getCapacity(),
                    room.getSquareMeter(),
                    room.isTelevision(),
                    room.isMinibar(),
                    room.isGameConsole(),
                    room.isCashBox(),
                    room.isProjection()
            };
            model.addRow(row);
        }

        if (tblRooms.getRowCount() > 0) {
            int selectedRow = tblRooms.getSelectedRow();
            if (selectedRow != -1) {
                Room selectedRoom = rooms.get(selectedRow);
                tfStock.setText(String.valueOf(selectedRoom.getStock()));
                tfPriceAdult.setText(String.valueOf(selectedRoom.getPriceAdult()));
                tfPriceChild.setText(String.valueOf(selectedRoom.getPriceChild()));
                tfCapacity.setText(String.valueOf(selectedRoom.getCapacity()));
                tfSquareMeter.setText(String.valueOf(selectedRoom.getSquareMeter()));
                cbTelevision.setSelected(selectedRoom.isTelevision());
                cbMinibar.setSelected(selectedRoom.isMinibar());
                cbGameConsole.setSelected(selectedRoom.isGameConsole());
                cbSafeBox.setSelected(selectedRoom.isCashBox());
                cbProjection.setSelected(selectedRoom.isProjection());
                btnSave.setText("Update");
            } else {
                clearForm();
                btnSave.setText("Save");
            }
        } else {
            clearForm();
            btnSave.setText("Save");
        }
    }

    private void clearForm() {
        cbHotel.setSelectedIndex(0);
        cbRoomType.setSelectedIndex(0);
        tfStock.setText("");
        tfPriceAdult.setText("");
        tfPriceChild.setText("");
        tfCapacity.setText("");
        tfSquareMeter.setText("");
        cbTelevision.setSelected(false);
        cbMinibar.setSelected(false);
        cbGameConsole.setSelected(false);
        cbSafeBox.setSelected(false);
        cbProjection.setSelected(false);
    }

    private void deleteRoom() {
        int selectedRow = tblRooms.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int roomId = (int) tblRooms.getValueAt(selectedRow, 0);
        if (roomManager.delete(roomId)) {
            JOptionPane.showMessageDialog(this, "Room deleted successfully!");
            listRooms();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete the room.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRoom() {
        int selectedRow = tblRooms.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Room room = new Room();
        room.setId((int) tblRooms.getValueAt(selectedRow, 0));
        room.setType((String) cbRoomType.getSelectedItem());
        room.setStock(Integer.parseInt(tfStock.getText()));
        room.setPriceAdult(Double.parseDouble(tfPriceAdult.getText()));
        room.setPriceChild(Double.parseDouble(tfPriceChild.getText()));
        room.setCapacity(Integer.parseInt(tfCapacity.getText()));
        room.setSquareMeter(Integer.parseInt(tfSquareMeter.getText()));
        room.setTelevision(cbTelevision.isSelected());
        room.setMinibar(cbMinibar.isSelected());
        room.setGameConsole(cbGameConsole.isSelected());
        room.setCashBox(cbSafeBox.isSelected());
        room.setProjection(cbProjection.isSelected());

        Set<String> updatedFields = new HashSet<>();
        updatedFields.add("stock");
        updatedFields.add("adultPrice");
        updatedFields.add("childPrice");
        updatedFields.add("capacity");
        updatedFields.add("squareMeter");
        updatedFields.add("television");
        updatedFields.add("minibar");
        updatedFields.add("gameConsole");
        updatedFields.add("cashBox");
        updatedFields.add("projection");

        if (roomManager.update(room, updatedFields)) {
            JOptionPane.showMessageDialog(this, "Room updated successfully!");
            listRooms();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update the room.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddRoomView::new);
    }
}
