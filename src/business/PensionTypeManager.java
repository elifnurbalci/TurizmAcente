package business;

import dao.PensionTypeDao;
import entities.PensionType;
import javax.swing.*;
import java.util.List;

public class PensionTypeManager {
    private PensionTypeDao pensionTypeDao;

    public PensionTypeManager() {
        this.pensionTypeDao = new PensionTypeDao();
    }

    public void loadPensionTypes(JComboBox<String> comboBox) {
        List<PensionType> pensions = pensionTypeDao.getAllPensionTypes();
        comboBox.removeAllItems();
        for (PensionType pension : pensions) {
            comboBox.addItem(pension.getName());
        }
    }

    public int findPensionTypeIdByName(String pensionTypeName) {
        List<PensionType> pensions = pensionTypeDao.getAllPensionTypes();
        for (PensionType pension : pensions) {
            if (pension.getName().equals(pensionTypeName)) {
                return pension.getId();
            }
        }
        return -1;
    }

    public List<PensionType> getPensionsByHotel(int hotelId) {
        return pensionTypeDao.getPensionsByHotel(hotelId);
    }
}
