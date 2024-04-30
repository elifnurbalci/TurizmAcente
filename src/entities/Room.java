package entities;

public class Room {
    private int id;
    private int hotelId;
    private String type;
    private int stock;
    private int capacity;
    private int squareMeter;
    private boolean television;
    private boolean minibar;
    private boolean gameConsole;
    private boolean cashBox;
    private boolean projection;
    private Hotel hotel;

    // Constructor
    public Room() {
    }

    public Room(int id, String type, int stock, int capacity, int squareMeter, boolean television, boolean minibar, boolean gameConsole, boolean cashBox, boolean projection) {
        this.id = id;
        this.type = type;
        this.stock = stock;
        this.capacity = capacity;
        this.squareMeter = squareMeter;
        this.television = television;
        this.minibar = minibar;
        this.gameConsole = gameConsole;
        this.cashBox = cashBox;
        this.projection = projection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSquareMeter() {
        return squareMeter;
    }

    public void setSquareMeter(int squareMeter) {
        this.squareMeter = squareMeter;
    }

    public boolean isTelevision() {
        return television;
    }

    public void setTelevision(boolean television) {
        this.television = television;
    }

    public boolean isMinibar() {
        return minibar;
    }

    public void setMinibar(boolean minibar) {
        this.minibar = minibar;
    }

    public boolean isGameConsole() {
        return gameConsole;
    }

    public void setGameConsole(boolean gameConsole) {
        this.gameConsole = gameConsole;
    }

    public boolean isCashBox() {
        return cashBox;
    }

    public void setCashBox(boolean cashBox) {
        this.cashBox = cashBox;
    }

    public boolean isProjection() {
        return projection;
    }

    public void setProjection(boolean projection) {
        this.projection = projection;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    @Override
    public String toString() {
        return type;
    }

}
