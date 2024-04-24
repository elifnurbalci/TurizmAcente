package ui;

import business.UserManager;
import dataAccess.Helper;
import entities.Role;
import entities.User;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoginView extends JFrame {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JLabel lbl_login;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JButton btn_login;
    private final UserManager userManager;

    public LoginView(JFrame mainFrame) {
        this.userManager = new UserManager();
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);
        btn_login.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_username,this.fld_password})){
                Helper.showMessage("fill");
            } else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(),this.fld_password.getText());
                if (loginUser == null) {
                    Helper.showMessage("notFound");
                } else {
                    dispose();
                    if (Objects.equals(loginUser.getRole(), "admin")) {
                        JFrame adminFrame = new JFrame("Tourism Agency System");
                        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        adminFrame.setSize(800, 600);

                        // Create a new adminPanel instance and set it as the content pane
                        AdminUserView adminPanel = new AdminUserView(adminFrame);
                        adminFrame.setContentPane(adminPanel);

                        // Make the frame visible to the user
                        adminFrame.setVisible(true);
                    } else if (Objects.equals(loginUser.getRole(), "agent")) {
                        JFrame agentFrame = new JFrame();
                        agentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        agentFrame.setSize(800, 600);

                        // Create a new adminPanel instance and set it as the content pane
                        EmployeeView employeePanel = new EmployeeView(agentFrame);
                        agentFrame.setContentPane(employeePanel);

                        // Make the frame visible to the user
                        agentFrame.setVisible(true);

                    }
                }
            }
        });
    }
}
