import ui.LoginView;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set look and feel.");
        }

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LoginView loginPanel = new LoginView();
        mainFrame.setContentPane(loginPanel);

        mainFrame.pack();
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }
}
