package ug.visituganda.visituganda.dto.request;

import jakarta.persistence.Column;

public class CreateCarRequest {

    private String title;

    @Column(name = "description")
    private String description;

    private Double rating;
    private Double costPerDay;
    private String manufacturer;
    private Integer year;
    private String transmission;
    private String drivetrain;
    private Integer seating;
    private String fuelConsumption;
    private String vechicleNumber;
    private String about;

    public CreateCarRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getRating() {
        return rating;
    }

    public Double getCostPerDay() {
        return costPerDay;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getYear() {
        return year;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    public Integer getSeating() {
        return seating;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public String getVechicleNumber() {
        return vechicleNumber;
    }

    public String getAbout() {
        return about;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setCostPerDay(Double costPerDay) {
        this.costPerDay = costPerDay;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void setSeating(Integer seating) {
        this.seating = seating;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public void setVechicleNumber(String vechicleNumber) {
        this.vechicleNumber = vechicleNumber;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}