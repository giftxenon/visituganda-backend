package ug.visituganda.visituganda.controller.serviceProvider;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ug.visituganda.visituganda.dto.request.CreateCarRequest;
import ug.visituganda.visituganda.modal.CreateCar;
import ug.visituganda.visituganda.service.serviceProvider.CreateCarService;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.repository.serviceProvider.CarImageRepository;
import ug.visituganda.visituganda.repository.serviceProvider.CompanyRegisterRepository;
import ug.visituganda.visituganda.repository.serviceProvider.CreateCarRepository;
import ug.visituganda.visituganda.modal.CarImage;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@CrossOrigin(origins = "*")
public class CreateCarController {

    private final CreateCarService createCarService;
    private final CompanyRegisterRepository companyRegisterRepository;
    private final CreateCarRepository createCarRepository;
    private final CarImageRepository carImageRepository;

    public CreateCarController(CreateCarService createCarService,
                               CompanyRegisterRepository companyRegisterRepository,
                               CreateCarRepository createCarRepository,
                               CarImageRepository carImageRepository) {
        this.createCarService = createCarService;
        this.companyRegisterRepository = companyRegisterRepository;
        this.createCarRepository = createCarRepository;
        this.carImageRepository = carImageRepository;
    }

    // POST new car with up to 4 images
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<?> createCar(
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double rating,
            @RequestParam Double costPerDay,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String transmission,
            @RequestParam(required = false) String drivetrain,
            @RequestParam(required = false) Integer seating,
            @RequestParam(required = false) String fuelConsumption,
            @RequestParam(required = false) String vechicleNumber,
            @RequestParam(required = false) String about,
            @RequestParam(required = false) List<MultipartFile> images,
            @AuthenticationPrincipal User user
    ) {
        try {
            if (images != null && images.size() > 4) {
                return ResponseEntity.badRequest().body("Maximum 4 images allowed");
            }

            CreateCarRequest request = new CreateCarRequest();
            request.setTitle(title);
            request.setDescription(description);
            request.setRating(rating);
            request.setCostPerDay(costPerDay);
            request.setManufacturer(manufacturer);
            request.setYear(year);
            request.setTransmission(transmission);
            request.setDrivetrain(drivetrain);
            request.setSeating(seating);
            request.setFuelConsumption(fuelConsumption);
            request.setVechicleNumber(vechicleNumber);
            request.setAbout(about);

            return ResponseEntity.ok(
                    createCarService.createCar(request, images, user.getUsername())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to create car: " + e.getMessage());
        }
    }

    // GET single image by image ID
    @GetMapping("/images/{imageId}")
    public ResponseEntity<byte[]> getCarImage(@PathVariable Long imageId) {
        CarImage image = carImageRepository.findById(imageId).orElse(null);
        if (image == null || image.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image.getImageData());
    }

    // GET all image IDs for a car
    @GetMapping("/{id}/images")
    public ResponseEntity<List<Long>> getCarImageIds(@PathVariable Long id) {
        List<CarImage> images = carImageRepository.findByCarId(id);
        List<Long> ids = images.stream().map(CarImage::getId).toList();
        return ResponseEntity.ok(ids);
    }

    // GET all cars — public
    @GetMapping
    public List<CreateCar> getAllCars() {
        return createCarService.getAllCars();
    }

    // GET single car by id — public
    @GetMapping("/{id}")
    public CreateCar getCar(@PathVariable Long id) {
        return createCarService.getCarById(id);
    }

    // GET cars for logged-in company
    @GetMapping("/my-cars")
    @PreAuthorize("hasRole('BUSINESS')")
    public List<CreateCar> getCarsForMyCompany(@AuthenticationPrincipal User user) {
        return companyRegisterRepository.findByOwnerId(user.getId())
                .map(company -> createCarService.getCarsByCompanyId(company.getId()))
                .orElseThrow(() -> new RuntimeException("User does not have a registered company yet"));
    }

    // GET cars by company ID — public
    @GetMapping("/company/{companyId}")
    public List<CreateCar> getCarsByCompany(@PathVariable Long companyId) {
        return createCarService.getCarsByCompanyId(companyId);
    }
}