package ug.visituganda.visituganda.service.serviceProvider;

import org.springframework.web.multipart.MultipartFile;
import ug.visituganda.visituganda.entity.company.CompanyRegister;
import ug.visituganda.visituganda.modal.CreateCar;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;

import java.io.IOException;
import java.util.List;

public interface CompanyRegisterService {

    CompanyRegister registerBusiness(
            String companyName,
            BusinessCategory category,
            String location,
            String phone,
            String email,
            String operatingHours,
            String description,
            MultipartFile logo,
            String username
    ) throws IOException;

    // =========================================
    // 🔥 NEW: CREATE COMPANY AND CAR IN SINGLE REQUEST
    // =========================================
    CompanyRegister registerBusinessWithCar(
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
    ) throws IOException;

    // =========================================
    // 🔥 FETCH ALL REGISTERED BUSINESSES
    // =========================================
    List<CompanyRegister> getAllBusinesses();

    CompanyRegister getBusinessById(Long id);
}