package business;

import entities.PensionType;
import dao.PensionTypeDao;
import java.util.ArrayList;

public class PensionTypeManager {
    private PensionTypeDao pensionTypeDao;

    public PensionTypeManager() {
        pensionTypeDao = new PensionTypeDao();
    }

    public ArrayList<PensionType> getAllPensionTypes() {
        return pensionTypeDao.findAll();
    }
}
