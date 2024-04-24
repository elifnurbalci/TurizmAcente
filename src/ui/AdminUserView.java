package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserView extends JPanel {
    private JPanel w_top;
    private JLabel lbl_title;
    private JPanel container;
    private JPanel w_bottom;
    private JButton btn_add_user;
    private JButton btn_update_user;
    private JButton DELETEUSERButton;
    private JButton btn_admin_logout;

    public AdminUserView(JFrame loginUser) {
        this.setLayout(new BorderLayout()); // Setting the layout to BorderLayout as it's not specified
        container = new JPanel(); // Creating the JPanel container
        this.add(container, BorderLayout.CENTER); // Adding the container JPanel to the center
        container.setLayout(new GridLayout(3, 1)); // Setting GridLayout for the container JPanel (e.g., 3 rows, 1 column)

        // Creating and adding components to the container JPanel
        btn_admin_logout = new JButton("Logout");
        btn_admin_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser.dispose();
            }
        });
        container.add(btn_admin_logout);

        btn_add_user = new JButton("Add User");
        btn_add_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser.dispose();
                JFrame addUserFrame = new JFrame();
                addUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                addUserFrame.setSize(800, 600);

                AddUserView addUserPanel = new AddUserView(addUserFrame);
                addUserFrame.setContentPane(addUserPanel);

                addUserFrame.setVisible(true);
            }
        });
        container.add(btn_add_user);

        btn_update_user = new JButton("Update User");
        btn_update_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser.dispose();
                JFrame updateUserFrame = new JFrame();
                updateUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                updateUserFrame.setSize(800, 600);

                UpdateUserView updateUserPanel = new UpdateUserView(updateUserFrame);
                updateUserFrame.setContentPane(updateUserPanel);

                updateUserFrame.setVisible(true);
            }
        });
        container.add(btn_update_user);

        DELETEUSERButton = new JButton("Delete User");
        DELETEUSERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser.dispose();
                JFrame deleteUserFrame = new JFrame();
                deleteUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                deleteUserFrame.setSize(800, 600);

                DeleteUserView deleteUserPanel = new DeleteUserView(deleteUserFrame);
                deleteUserFrame.setContentPane(deleteUserPanel);

                deleteUserFrame.setVisible(true);
            }
        });
        container.add(DELETEUSERButton);

        // Enabling visibility
        this.setVisible(true);
    }
}
