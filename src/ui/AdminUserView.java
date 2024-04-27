package ui;

import business.UserManager;
import dataAccess.Helper;

import javax.swing.*;
import java.awt.*;

public class AdminUserView extends JFrame {
    private JPanel container;
    private JButton btn_add_user, btn_edit_user, btn_delete_user;
    private final UserManager userManager;

    public AdminUserView() {
        this.userManager = new UserManager();
        this.container = new JPanel();

        this.btn_add_user = new JButton("ADD USER");
        this.btn_edit_user = new JButton("EDIT USER");
        this.btn_delete_user = new JButton("DELETE USER");

        initializeComponents();
        initListeners();
    }

    private void initializeComponents() {
        this.container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));


        this.btn_add_user.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.btn_add_user.setFont(new Font("Arial", Font.PLAIN, 16));
        this.btn_edit_user.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.btn_edit_user.setFont(new Font("Arial", Font.PLAIN, 16));
        this.btn_delete_user.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.btn_delete_user.setFont(new Font("Arial", Font.PLAIN, 16));

        setTitle("Admin Management System");
        this.container.add(Box.createVerticalStrut(40));
        this.container.add(btn_add_user);
        this.container.add(Box.createVerticalStrut(10));
        this.container.add(btn_edit_user);
        this.container.add(Box.createVerticalStrut(10));
        this.container.add(btn_delete_user);
        this.container.add(Box.createVerticalStrut(10));

        add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 450, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setContentPane(container);
    }
    private void initListeners() {
        btn_add_user.addActionListener(e -> {
            AddUserView addUserView = new AddUserView();
            addUserView.setVisible(true);
        });

        btn_edit_user.addActionListener(e -> {
            UpdateUserView updateUserView = new UpdateUserView();
            updateUserView.setVisible(true);
        });

        btn_delete_user.addActionListener(e -> {
            DeleteUserView deleteUserView = new DeleteUserView();
            deleteUserView.setVisible(true);
        });
    }

}
