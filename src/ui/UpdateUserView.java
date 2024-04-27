package ui;

import business.UserManager;
import entities.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UpdateUserView extends JFrame {
    private JPanel container;
    private JLabel lbl_username, lbl_password, lbl_role;
    private JComboBox<String> cbox_select_user, cbox_select_role;
    private JButton btn_update_user, btn_back;
    private JPasswordField fld_password;
    private final UserManager userManager;

    public UpdateUserView() {
        userManager = new UserManager();
        container = new JPanel();
        lbl_username = new JLabel("Username:");
        lbl_password = new JLabel("Password:");
        lbl_role = new JLabel("Role:");
        cbox_select_user = new JComboBox<>();
        cbox_select_role = new JComboBox<>(new String[]{"admin", "employee"});  // Rolleri güncelledim
        btn_update_user = new JButton("UPDATE");
        fld_password = new JPasswordField(20);
        btn_back = new JButton("BACK");
        initializeComponents();
        initListeners();
        loadUserNames();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(container);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(Box.createVerticalStrut(10));
        lbl_username.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(lbl_username);
        container.add(Box.createVerticalStrut(5));
        cbox_select_user.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        container.add(cbox_select_user);

        container.add(Box.createVerticalStrut(5));
        lbl_password.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(lbl_password);
        container.add(Box.createVerticalStrut(5));
        fld_password.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        container.add(fld_password);
        container.add(Box.createVerticalStrut(5));
        lbl_role.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(lbl_role);
        container.add(Box.createVerticalStrut(5));
        cbox_select_role.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        container.add(cbox_select_role);
        container.add(Box.createVerticalStrut(5));
        btn_update_user.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(btn_update_user);
        container.add(Box.createVerticalStrut(5));
        btn_back.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(btn_back);
    }

    private void initListeners() {
        btn_update_user.addActionListener(e -> {
            String selectedUser = cbox_select_user.getSelectedItem().toString();
            String password = new String(fld_password.getPassword()).trim();
            String role = cbox_select_role.getSelectedItem().toString();

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean success = userManager.updateUser(selectedUser, password, role);
                if (success) {
                    JOptionPane.showMessageDialog(this, "User updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn_back.addActionListener(e -> {
            dispose();
        });
    }

    private void loadUserNames() {
        List<User> users = userManager.getAllUsers();
        for (User user : users) {
            cbox_select_user.addItem(user.getUsername());
        }
    }
}
