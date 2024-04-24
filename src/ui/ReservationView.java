package ui;
import javax.swing.*;
import java.awt.*;

public class ReservationView extends JFrame{

    private JPanel w_top;
    private JLabel lbl_title;
    private JPanel container;
    private JPanel w_bottom;
    private JTabbedPane tab_pnl_main;
    private JPanel tab_make_reservation;
    private JLabel lbl_select_room_type;
    private JLabel lbl_select_number_of_adult;
    private JLabel lbl_select_number_of_children;
    private JComboBox cbox_select_room_type;
    private JLabel lbl_select_hotel;
    private JComboBox cbox_select_hotel;
    private JPanel tab_reservation_list;
    private JTable tlb_hotel_list;
    private JButton btn_cancel_reservation_list;
    private JFormattedTextField fld_checkin_date;
    private JFormattedTextField fld_checkout_date;
    private JLabel lbl_checkin_date;
    private JLabel lbl_checkout_date;
    private JComboBox cbox_select_number_of_adult;
    private JComboBox cbox_select_number_of_children;
    private JComboBox cbox_select_pension_type;
    private JLabel lbl_select_pension_type;
    private JButton btn_make_reservation_cancel;
    private JButton btn_make_reservation;
    private JLabel lbl_customer_info;
    private JTextField fld_customer_full_name;
    private JTextField fld_customer_email;
    private JTextField fld_customer_phone_number;
    private JLabel lbl_customer_full_name;
    private JLabel lbl_customer_email;
    private JLabel lbl_customer_phone_number;

    public ReservationView(JFrame reservationFrame) {
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800,600);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);
    }
}
