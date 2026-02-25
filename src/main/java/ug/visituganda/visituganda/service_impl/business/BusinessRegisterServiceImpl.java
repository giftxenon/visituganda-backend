package ug.visituganda.visituganda.service_impl.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ug.visituganda.visituganda.entity.Business.BusinessRegister;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;
import ug.visituganda.visituganda.repository.business.BusinessRegisterRepository;
import ug.visituganda.visituganda.repository.UserRepository;
import ug.visituganda.visituganda.service.business.BusinessRegisterService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessRegisterServiceImpl implements BusinessRegisterService {

    private final BusinessRegisterRepository businessRegisterRepository;
    private final UserRepository userRepository;

    @Override
    public BusinessRegister registerBusiness(
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

        BusinessRegister business = BusinessRegister.builder()
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

        return businessRegisterRepository.save(business);
    }

    // =========================================
    // 🔥 NEW: FETCH ALL REGISTERED BUSINESSES
    // =========================================
    @Override
    public List<BusinessRegister> getAllBusinesses() {
        return businessRegisterRepository.findAll();
    }

    @Override
    public BusinessRegister getBusinessById(Long id) {
        return businessRegisterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Business not found"));
    }
}