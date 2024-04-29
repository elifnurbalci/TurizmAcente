package entities;

public class HotelPensionType {
    private int hotelId;
    private int pensionTypeId;

    public HotelPensionType() {
    }

    public HotelPensionType(int hotelId, int pensionTypeId) {
        this.hotelId = hotelId;
        this.pensionTypeId = pensionTypeId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getPensionTypeId() {
        return pensionTypeId;
    }

    public void setPensionTypeId(int pensionTypeId) {
        this.pensionTypeId = pensionTypeId;
    }
}
