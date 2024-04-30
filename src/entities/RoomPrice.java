package entities;

public class RoomPrice {
    private int roomId;
    private int hotelId;
    private int seasonId;
    private int pensionTypeId;
    private double adultPrice;
    private double childPrice;

    public RoomPrice() {}

    public RoomPrice(int roomId, int hotelId, int seasonId, int pensionTypeId, double adultPrice, double childPrice) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.seasonId = seasonId;
        this.pensionTypeId = pensionTypeId;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getPensionTypeId() {
        return pensionTypeId;
    }

    public void setPensionTypeId(int pensionTypeId) {
        this.pensionTypeId = pensionTypeId;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(double childPrice) {
        this.childPrice = childPrice;
    }
}
