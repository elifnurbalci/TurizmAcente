package entities;

import java.util.List;

public class Hotel {
    private int id;
    private String name;
    private String city;
    private String region;
    private String address;
    private String email;
    private String phone;
    private String starRating;
    private Boolean carPark;
    private Boolean wifi;
    private Boolean pool;
    private Boolean fitness;
    private Boolean concierge;
    private Boolean spa;
    private Boolean roomService;
    private List<Season> seasons;
    private List<PensionType> pensionTypes;

    // Constructors
    public Hotel() {}

    public Hotel(int id, String name, String city, String region, String address, String email, String phone, String starRating,
                 Boolean carPark, Boolean wifi, Boolean pool, Boolean fitness, Boolean concierge, Boolean spa, Boolean roomService, List<Integer> seasonIds, List<Integer> pensionTypeIds) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.region = region;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.starRating = starRating;
        this.carPark = carPark;
        this.wifi = wifi;
        this.pool = pool;
        this.fitness = fitness;
        this.concierge = concierge;
        this.spa = spa;
        this.roomService = roomService;
        this.seasons = findSeasonsByIds(seasonIds);
        this.pensionTypes = findPensionsByIds(pensionTypeIds);
    }
    public Hotel(int id, List<Integer> seasonIds, List<Integer> pensionTypeIds) {
        this.id = id;
        this.seasons = findSeasonsByIds(seasonIds);
        this.pensionTypes = findPensionsByIds(pensionTypeIds);
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getStarRating() { return starRating; }
    public void setStarRating(String starRating) { this.starRating = starRating; }
    public Boolean getCarPark() { return carPark; }
    public void setCarPark(Boolean carPark) { this.carPark = carPark; }
    public Boolean getWifi() { return wifi; }
    public void setWifi(Boolean wifi) { this.wifi = wifi; }
    public Boolean getPool() { return pool; }
    public void setPool(Boolean pool) { this.pool = pool; }
    public Boolean getFitness() { return fitness; }
    public void setFitness(Boolean fitness) { this.fitness = fitness; }
    public Boolean getConcierge() { return concierge; }
    public void setConcierge(Boolean concierge) { this.concierge = concierge; }
    public Boolean getSpa() { return spa; }
    public void setSpa(Boolean spa) { this.spa = spa; }
    public Boolean getRoomService() { return roomService; }
    public void setRoomService(Boolean roomService) { this.roomService = roomService; }


    private List<Season> findSeasonsByIds(List<Integer> seasonIds) {
        return null;
    }
    private List<PensionType> findPensionsByIds(List<Integer> pensionTypeIds) {
        return null;
    }
    @Override
    public String toString() {
        return name;
    }

}
