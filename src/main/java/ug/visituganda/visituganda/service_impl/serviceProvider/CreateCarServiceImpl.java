package ug.visituganda.visituganda.service_impl.serviceProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ug.visituganda.visituganda.dto.request.CreateCarRequest;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.CarImage;
import ug.visituganda.visituganda.modal.CreateCar;
import ug.visituganda.visituganda.entity.company.CompanyRegister;
import ug.visituganda.visituganda.repository.serviceProvider.CompanyRegisterRepository;
import ug.visituganda.visituganda.repository.serviceProvider.CreateCarRepository;
import ug.visituganda.visituganda.repository.UserRepository;
import ug.visituganda.visituganda.service.serviceProvider.CreateCarService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateCarServiceImpl implements CreateCarService {

    private final CreateCarRepository createCarRepository;
    private final UserRepository userRepository;
    private final CompanyRegisterRepository companyRegisterRepository;

    @Override
    public CreateCar createCar(CreateCarRequest request, List<MultipartFile> images, String username) throws IOException {
        // Fetch logged-in user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        // Fetch company by owner
        CompanyRegister company = companyRegisterRepository.findByOwnerId(user.getId())
                .orElseThrow(() -> new RuntimeException("User does not have a registered company yet"));

        CreateCar car = new CreateCar();
        car.setTitle(request.getTitle());
        car.setDescription(request.getDescription());
        car.setRating(request.getRating());
        car.setCostPerDay(request.getCostPerDay());
        car.setManufacturer(request.getManufacturer());
        car.setYear(request.getYear());
        car.setTransmission(request.getTransmission());
        car.setDrivetrain(request.getDrivetrain());
        car.setSeating(request.getSeating());
        car.setFuelConsumption(request.getFuelConsumption());
        car.setVechicleNumber(request.getVechicleNumber());
        car.setAbout(request.getAbout());
        car.setCompany(company);

        // ✅ Save car first so CarImage can reference its ID
        CreateCar savedCar = createCarRepository.save(car);

        // ✅ Attach up to 4 images as CarImage records
        if (images != null) {
            for (MultipartFile file : images) {
                if (file != null && !file.isEmpty()) {
                    CarImage carImage = new CarImage();
                    carImage.setImageData(file.getBytes());
                    carImage.setImageFileName(file.getOriginalFilename());
                    carImage.setCar(savedCar);
                    savedCar.getImages().add(carImage);
                }
            }
            createCarRepository.save(savedCar);
        }

        return savedCar;
    }

    @Override
    public List<CreateCar> getAllCars() {
        return createCarRepository.findAll();
    }

    @Override
    public CreateCar getCarById(Long id) {
        return createCarRepository.findById(id).orElse(null);
    }

    @Override
    public List<CreateCar> getCarsByCompanyId(Long companyId) {
        return createCarRepository.findByCompanyId(companyId);
    }

    @Override
    public CreateCar addCarToCompany(Long companyId, CreateCar createCar) {
        createCar.addCarToCompany(companyId);
        return createCarRepository.save(createCar);
    }
}