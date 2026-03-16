package ug.visituganda.visituganda.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ug.visituganda.visituganda.entity.company.CompanyRegister;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
public class CreateCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 500)
    private String description;

    private String img;

    // ✅ kept for backward compat but no longer used for new uploads
    @JsonIgnore
    @Column(name = "image_data", columnDefinition = "bytea")
    private byte[] imageData;

    @Column(name = "image_file_name")
    private String imageFileName;

    private Double rating;
    private Double costPerDay;
    private String manufacturer;
    private Integer year;
    private String transmission;
    private String drivetrain;
    private Integer seating;
    private String fuelConsumption;
    private String vechicleNumber;

    @Column(name = "company_id")
    private Long companyId;

    @Column(length = 1000)
    private String about;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyRegister company;

    // ✅ NEW: up to 4 images stored in car_images table
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarImage> images = new ArrayList<>();

    public CreateCar() {}

    // ---------- Getters ----------
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImg() { return img; }
    public byte[] getImageData() { return imageData; }
    public String getImageFileName() { return imageFileName; }
    public Double getRating() { return rating; }
    public Double getCostPerDay() { return costPerDay; }
    public String getManufacturer() { return manufacturer; }
    public Integer getYear() { return year; }
    public String getTransmission() { return transmission; }
    public String getDrivetrain() { return drivetrain; }
    public Integer getSeating() { return seating; }
    public String getFuelConsumption() { return fuelConsumption; }
    public String getVechicleNumber() { return vechicleNumber; }
    public Long getCompanyId() { return companyId; }
    public String getAbout() { return about; }
    public CompanyRegister getCompany() { return company; }
    public List<CarImage> getImages() { return images; }

    // ---------- Setters ----------
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setImg(String img) { this.img = img; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }
    public void setImageFileName(String imageFileName) { this.imageFileName = imageFileName; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setCostPerDay(Double costPerDay) { this.costPerDay = costPerDay; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setYear(Integer year) { this.year = year; }
    public void setTransmission(String transmission) { this.transmission = transmission; }
    public void setDrivetrain(String drivetrain) { this.drivetrain = drivetrain; }
    public void setSeating(Integer seating) { this.seating = seating; }
    public void setFuelConsumption(String fuelConsumption) { this.fuelConsumption = fuelConsumption; }
    public void setVechicleNumber(String vechicleNumber) { this.vechicleNumber = vechicleNumber; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public void setAbout(String about) { this.about = about; }
    public void setImages(List<CarImage> images) { this.images = images; }

    public void setCompany(CompanyRegister company) {
        this.company = company;
        if (company != null) {
            this.companyId = company.getId();
        }
    }

    public void addCarToCompany(Long companyId) {
        this.companyId = companyId;
    }
}