package ui;

import javax.swing.*;
import java.awt.*;

public class AddUserView extends JFrame{

    private JPanel container;
    private JPanel w_top;
    private JPanel w_bottom;
    private JLabel lbl_title;
    private JLabel lbl_username;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JLabel lbl_password;
    private JButton btn_add_user;
    private JLabel lbl_role;
    private JComboBox cbox_role;
    private JButton btn_cancel;

    public AddUserView(JFrame addUserFrame) {
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);
    }
}
