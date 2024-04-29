package ui;

import business.UserManager;
import entities.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminUserView extends JFrame {
    private JPanel container;
    private JButton btn_add_user, btn_edit_user, btn_delete_user, btn_refresh;
    private JTable userTable;
    private JScrollPane scrollPane;
    private final UserManager userManager;

    public AdminUserView() {
        userManager = new UserManager();
        container = new JPanel();
        btn_add_user = new JButton("ADD USER");
        btn_edit_user = new JButton("EDIT USER");
        btn_delete_user = new JButton("DELETE USER");
        btn_refresh = new JButton("REFRESH");
        userTable = new JTable();
        scrollPane = new JScrollPane(userTable);

        initializeComponents();
        initListeners();
        loadUserData();
    }

    private void initializeComponents() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        btn_add_user.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_edit_user.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_delete_user.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_refresh.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn_add_user.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_edit_user.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_delete_user.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_refresh.setFont(new Font("Arial", Font.PLAIN, 16));

        setupUserTable();

        container.add(Box.createVerticalStrut(40));
        container.add(btn_add_user);
        container.add(Box.createVerticalStrut(10));
        container.add(btn_edit_user);
        container.add(Box.createVerticalStrut(10));
        container.add(btn_delete_user);
        container.add(Box.createVerticalStrut(40));
        container.add(btn_refresh);
        container.add(Box.createVerticalStrut(5));
        container.add(scrollPane);

        setTitle("Admin Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(container);
    }

    private void setupUserTable() {
        userTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Username", "Role"}
        ));
        scrollPane.setPreferredSize(new Dimension(400, 150));
    }

    private void loadUserData() {
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);
        for (User user : userManager.getAllUsers()) {
            model.addRow(new Object[]{user.getId(), user.getUsername(), user.getRole()});
        }
    }

    private void initListeners() {
        btn_add_user.addActionListener(e -> new AddUserView().setVisible(true));
        btn_edit_user.addActionListener(e -> new UpdateUserView().setVisible(true));
        btn_delete_user.addActionListener(e -> new DeleteUserView().setVisible(true));
        btn_refresh.addActionListener(e -> loadUserData());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminUserView().setVisible(true));
    }
}
