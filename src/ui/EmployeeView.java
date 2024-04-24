package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame {
    private JPanel w_top;
    private JLabel lbl_title;
    private JPanel w_bottom;
    private JButton btn_hotel_management;
    private JButton btn_reservation_management;
    private JButton btn_employee_logout;
    private JPanel container;

    public EmployeeView(JFrame agentFrame) {
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);
        btn_employee_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
//                JFrame mainFrame = new JFrame();
//                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                mainFrame.setSize(800, 600);
//
//                LoginView loginPanel = new LoginView(mainFrame);
//                mainFrame.setContentPane(loginPanel);
//
//                mainFrame.setVisible(true);
            }
        });
        btn_reservation_management.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JFrame reservationFrame = new JFrame();
                reservationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                reservationFrame.setSize(1800, 1600);

                ReservationView reservationPanel = new ReservationView(reservationFrame);
                reservationFrame.setContentPane(reservationPanel);

                reservationFrame.setVisible(true);
            }
        });
        btn_hotel_management.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JFrame hotelFrame = new JFrame();
                hotelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                hotelFrame.setSize(1800, 1600);

                HotelView hotelPanel = new HotelView();
                hotelFrame.setContentPane(hotelPanel);

                hotelFrame.setVisible(true);
            }
        });
    }
}
