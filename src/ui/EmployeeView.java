package ui;

import business.UserManager;

import javax.swing.*;
import java.awt.*;

public class EmployeeView extends JFrame{
    private JPanel container;
    private JButton btn_hotel_management, btn_reservation_management;
    private final UserManager userManager;


    public EmployeeView() {
        this.userManager = new UserManager();
        this.container = new JPanel();
        this.btn_hotel_management = new JButton("HOTEL MANAGEMENT");
        this.btn_reservation_management = new JButton("RESERVATION MANAGEMENT");

        initializeComponents();
        initListeners();
    }
    private void initializeComponents() {
        this.container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        this.btn_hotel_management.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.btn_reservation_management.setAlignmentX(Component.CENTER_ALIGNMENT);

        setTitle("Agent Management System");
        this.container.add(Box.createVerticalStrut(40));
        this.container.add(btn_hotel_management);
        this.container.add(Box.createVerticalStrut(10));
        this.container.add(btn_reservation_management);
        this.container.add(Box.createVerticalStrut(10));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        add(container);
        setVisible(true);

    }
    private void initListeners() {
        btn_hotel_management.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
                HotelView hotelView = new HotelView();
                hotelView.setVisible(true);
            });
        });
        btn_reservation_management.addActionListener(e -> {
//            this.dispose();
//            EventQueue.invokeLater(() -> {
//                ReservationView reservationView = new ReservationView();
//                reservationView.setVisible(true);
//            });
        });
    }
}
