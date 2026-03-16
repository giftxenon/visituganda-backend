package ug.visituganda.visituganda.service.serviceProvider;

import org.springframework.web.multipart.MultipartFile;
import ug.visituganda.visituganda.dto.request.CreateCarRequest;
import ug.visituganda.visituganda.modal.CreateCar;

import java.io.IOException;
import java.util.List;

public interface CreateCarService {

    // ✅ changed: MultipartFile image → List<MultipartFile> images
    CreateCar createCar(CreateCarRequest request, List<MultipartFile> images, String username) throws IOException;

    List<CreateCar> getAllCars();

    CreateCar getCarById(Long id);

    List<CreateCar> getCarsByCompanyId(Long companyId);

    CreateCar addCarToCompany(Long companyId, CreateCar createCar);
}