package dataAccess;

import javax.swing.*;

public class Helper {
    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static void showMessage(String message) {
        String msg;
        String title;
        switch (message) {
            case "fill":
                msg = "Please write username and password!";
                title = "Error!";
                break;
            case "done":
                msg = "Success!";
                title = "Result";
                break;
            case "notFound":
                msg = "User Not Found!";
                title = "Warning";
                break;
            case "error":
                msg = "Invalid input!";
                title = "Error!";
                break;
            default:
                msg = message;
                title = "Message";
                break;
        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fields) {
        for (JTextField field : fields) {
            if (isFieldEmpty(field)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSelected(JComboBox comboBox) {
        return !comboBox.getSelectedItem().toString().equals("");
    }

    public static boolean isChecked(JCheckBox checkBox) {
        return checkBox.isSelected();
    }
}
