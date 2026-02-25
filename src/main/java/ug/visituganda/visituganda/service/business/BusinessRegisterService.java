package ug.visituganda.visituganda.service.business;

import org.springframework.web.multipart.MultipartFile;
import ug.visituganda.visituganda.entity.Business.BusinessRegister;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;

import java.io.IOException;
import java.util.List;

public interface BusinessRegisterService {

    BusinessRegister registerBusiness(
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
    // 🔥 NEW: FETCH ALL REGISTERED BUSINESSES
    // =========================================
    List<BusinessRegister> getAllBusinesses();

    BusinessRegister getBusinessById(Long id);
}