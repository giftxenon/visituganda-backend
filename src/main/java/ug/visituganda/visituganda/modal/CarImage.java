package ug.visituganda.visituganda.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "car_images")
public class CarImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "image_data", columnDefinition = "bytea")
    private byte[] imageData;

    @Column(name = "image_file_name")
    private String imageFileName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private CreateCar car;

    public CarImage() {}

    public Long getId() { return id; }
    public byte[] getImageData() { return imageData; }
    public String getImageFileName() { return imageFileName; }
    public CreateCar getCar() { return car; }

    public void setId(Long id) { this.id = id; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }
    public void setImageFileName(String imageFileName) { this.imageFileName = imageFileName; }
    public void setCar(CreateCar car) { this.car = car; }
}
