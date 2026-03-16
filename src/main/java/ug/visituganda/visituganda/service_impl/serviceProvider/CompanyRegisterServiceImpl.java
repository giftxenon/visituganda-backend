package ug.visituganda.visituganda.service_impl.serviceProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ug.visituganda.visituganda.entity.company.CompanyRegister;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.CreateCar;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;
import ug.visituganda.visituganda.repository.serviceProvider.CompanyRegisterRepository;
import ug.visituganda.visituganda.repository.UserRepository;
import ug.visituganda.visituganda.repository.serviceProvider.CreateCarRepository;
import ug.visituganda.visituganda.service.serviceProvider.CompanyRegisterService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyRegisterServiceImpl implements CompanyRegisterService {

    private final CompanyRegisterRepository businessRegisterRepository;
    private final UserRepository userRepository;
    private final CreateCarRepository createCarRepository;

    @Override
    public CompanyRegister registerBusiness(
            String companyName,
            BusinessCategory category,
            String location,
            String phone,
            String email,
            String operatingHours,
            String description,
            MultipartFile logo,
            String username
    ) throws IOException {

        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        byte[] logoBytes = null;
        String logoFileName = null;

        if (logo != null && !logo.isEmpty()) {
            logoBytes = logo.getBytes();
            logoFileName = logo.getOriginalFilename();
        }

        CompanyRegister business = CompanyRegister.builder()
                .companyName(companyName)
                .category(category)
                .location(location)
                .phone(phone)
                .email(email)
                .operatingHours(operatingHours)
                .description(description)
                .logo(logoBytes)
                .logoFileName(logoFileName)
                .owner(owner)
                .build();

        // Save company
        CompanyRegister savedBusiness = businessRegisterRepository.save(business);

        // 🔹 Assign company to user and save user
        owner.setCompany(savedBusiness);
        userRepository.save(owner);

        return savedBusiness;
    }

    // =========================================
    // 🔥 CREATE COMPANY AND CAR IN SINGLE REQUEST
    // =========================================
    @Override
    public CompanyRegister registerBusinessWithCar(
            String companyName,
            BusinessCategory category,
            String location,
            String phone,
            String email,
            String operatingHours,
            String description,
            MultipartFile logo,
            CreateCar createCar,
            String username
    ) throws IOException {

        // First, create company
        CompanyRegister company = registerBusiness(
                companyName,
                category,
                location,
                phone,
                email,
                operatingHours,
                description,
                logo,
                username
        );

        // Then, associate car with the newly created company
        createCar.setCompanyId(company.getId());
        createCarRepository.save(createCar);

        return company;
    }

    // =========================================
    // 🔥 FETCH ALL REGISTERED BUSINESSES
    // =========================================
    @Override
    public List<CompanyRegister> getAllBusinesses() {
        return businessRegisterRepository.findAll();
    }

    @Override
    public CompanyRegister getBusinessById(Long id) {
        return businessRegisterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Business not found"));
    }
}