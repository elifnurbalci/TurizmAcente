package ui;

import business.UserManager;

import javax.swing.*;
import java.awt.*;

public class AddUserView extends JFrame{
    private JPanel container;
    private JLabel lbl_username, lbl_password, lbl_role;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JComboBox<String> cbox_role;
    private JButton btn_add_user, btn_back;;
    private final UserManager userManager;

    public AddUserView() {
        userManager = new UserManager();
        container = new JPanel();
        lbl_username = new JLabel("Username:");
        lbl_password = new JLabel("Password:");
        lbl_role = new JLabel("Role:");
        fld_username = new JTextField(20);
        fld_password = new JPasswordField(20);
        cbox_role = new JComboBox<>(new String[]{"admin", "employee"});
        btn_add_user = new JButton("ADD");
        btn_back = new JButton("BACK");

        initializeComponents();
        initListeners();

        setTitle("Add New User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(container);
        setVisible(true);
    }



    private void initializeComponents() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        lbl_username.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_username.setFont(new Font("Arial", Font.ITALIC, 14));
        fld_username.setMaximumSize(new Dimension(Integer.MAX_VALUE, fld_username.getPreferredSize().height));

        lbl_password.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_password.setFont(new Font("Arial", Font.ITALIC, 14));
        fld_password.setMaximumSize(new Dimension(Integer.MAX_VALUE, fld_password.getPreferredSize().height));

        lbl_role.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_role.setFont(new Font("Arial", Font.ITALIC, 14));
        cbox_role.setMaximumSize(new Dimension(Integer.MAX_VALUE, cbox_role.getPreferredSize().height));

        btn_add_user.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_back.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(Box.createVerticalStrut(20));
        container.add(lbl_username);
        container.add(fld_username);
        container.add(Box.createVerticalStrut(10));
        container.add(lbl_password);
        container.add(fld_password);
        container.add(Box.createVerticalStrut(10));
        container.add(lbl_role);
        container.add(cbox_role);
        container.add(Box.createVerticalStrut(20));
        container.add(btn_add_user);
        container.add(Box.createVerticalStrut(20));
        container.add(btn_back);
        container.add(Box.createVerticalStrut(10));
    }

    private void initListeners() {
        btn_add_user.addActionListener(e -> {
            String username = fld_username.getText().trim();
            String password = new String(fld_password.getPassword()).trim();
            String role = (String) cbox_role.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean success = userManager.addUser(username, password, role);
                if (success) {
                    JOptionPane.showMessageDialog(this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn_back.addActionListener(e -> {
            dispose();
        });
    }
}
