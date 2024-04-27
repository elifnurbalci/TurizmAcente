package ui;

import business.UserManager;
import dataAccess.Helper;
import entities.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JPanel container;
    private JLabel lbl_password, lbl_username;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;
    private final UserManager userManager;



    public LoginView() {
        this.userManager = new UserManager();

        this.container = new JPanel();

        this.lbl_username =  new JLabel("Username");
        this.lbl_password = new JLabel("Password");

        this.fld_username = new JTextField();
        this.fld_password = new JPasswordField();
        this.btn_login = new JButton("Login");

        initializeComponents();
        initListeners();
    }

    private void initializeComponents() {

        this.container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        this.lbl_username.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.lbl_username.setFont(new Font("Arial", Font.ITALIC, 14));
        this.fld_username.setMaximumSize(new Dimension(Integer.MAX_VALUE, fld_username.getPreferredSize().height));
        this.fld_username.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.lbl_password.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.lbl_password.setFont(new Font("Arial", Font.ITALIC, 14));
        this.fld_password.setMaximumSize(new Dimension(Integer.MAX_VALUE, fld_password.getPreferredSize().height));
        this.fld_password.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.btn_login.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.btn_login.setFont(new Font("Arial", Font.PLAIN, 16));

        setTitle("Tourism Agency System");
        this.container.add(Box.createVerticalStrut(40));
        this.container.add(lbl_username);
        this.container.add(Box.createVerticalStrut(5));
        this.container.add(fld_username);
        this.container.add(Box.createVerticalStrut(10));
        this.container.add(lbl_password);
        this.container.add(Box.createVerticalStrut(5));
        this.container.add(fld_password);
        this.container.add(Box.createVerticalStrut(10));
        this.container.add(btn_login);
        this.container.add(Box.createVerticalStrut(10));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        add(container);
        setVisible(true);
    }

    private void initListeners() {
        btn_login.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{fld_username, fld_password})) {
                Helper.showMessage("fill");
            } else {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String username = fld_username.getText();
        String password = new String(fld_password.getPassword());
        User loginUser = userManager.findByLogin(username, password);
        if (loginUser == null) {
            Helper.showMessage("notFound");
        } else {
            dispose();
            openUserPanel(loginUser);
        }
    }

    private void openUserPanel(User loginUser) {
        switch (loginUser.getRole().toLowerCase()) {
            case "admin":
                AdminUserView adminView = new AdminUserView();
                adminView.setVisible(true);
                break;
            case "agent":
                EmployeeView employeeView = new EmployeeView();
                employeeView.setVisible(true);
                break;
            default:
                Helper.showMessage("Role not supported!");
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginView::new);
    }
}
