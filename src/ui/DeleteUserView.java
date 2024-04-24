package ui;

import javax.swing.*;
import java.awt.*;

public class DeleteUserView extends JFrame{
    private JPanel w_top;
    private JLabel lbl_title;
    private JPanel w_bottom;
    private JLabel lbl_username;
    private JTextField fld_username;
    private JButton btn_add_user;
    private JButton btn_delete_user_cancel;
    private JPanel container;

    public DeleteUserView(JFrame updateUserFrame) {
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);
    }
}

