import ui.LoginView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Create the main frame for the application
        JFrame mainFrame = new JFrame("Tourism Agency System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        // Create a new LoginPanel instance and set it as the content pane
        LoginView loginPanel = new LoginView(mainFrame);
        mainFrame.setContentPane(loginPanel);

        // Make the frame visible to the user
        mainFrame.setVisible(true);
    }
}
