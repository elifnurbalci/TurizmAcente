package ui;

import javax.swing.*;
import java.awt.*;

public class UpdateUserView extends JFrame {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_title;
    private JPanel w_bottom;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JPasswordField fld_password;
    private JButton btn_add_user;
    private JLabel lbl_role;
    private JComboBox cbox_role;
    private JButton btn_update_user_cancel;
    private JComboBox cbox_select_user;

    public UpdateUserView(JFrame updateUserFrame) {
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);
    }
}
