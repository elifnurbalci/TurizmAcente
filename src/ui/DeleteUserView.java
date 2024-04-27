package ui;

import business.UserManager;
import javax.swing.*;
import java.awt.*;

public class DeleteUserView extends JFrame {
    private JPanel container;
    private JLabel lbl_username;
    private JTextField fld_username;
    private JButton btn_delete_user, btn_back;
    private final UserManager userManager;

    public DeleteUserView() {
        userManager = new UserManager();
        container = new JPanel();
        lbl_username = new JLabel();
        fld_username = new JTextField(20);
        btn_delete_user = new JButton();
        btn_back = new JButton();
        initializeComponents();
        initListeners();
    }

    private void initializeComponents() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        lbl_username.setText("Username:");
        lbl_username.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_username.setFont(new Font("Arial", Font.ITALIC, 14));
        fld_username.setMaximumSize(new Dimension(Integer.MAX_VALUE, fld_username.getPreferredSize().height));

        btn_delete_user.setText("DELETE");
        btn_delete_user.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn_back = new JButton("BACK");
        btn_back.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(Box.createVerticalStrut(40));
        container.add(lbl_username);
        container.add(Box.createVerticalStrut(10));
        container.add(fld_username);
        container.add(Box.createVerticalStrut(10));
        container.add(btn_delete_user);
        container.add(Box.createVerticalStrut(10));
        container.add(btn_back);
        container.add(Box.createVerticalStrut(10));

        setTitle("Delete User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        add(container);
        setVisible(true);
    }

    private void initListeners() {
        btn_delete_user.addActionListener(e -> {
            String username = fld_username.getText().trim();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean success = userManager.deleteUser(username);
                if (success) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn_back.addActionListener(e -> {
            dispose();
        });
    }

    public static void main(String[] args) {
        new DeleteUserView();
    }
}
