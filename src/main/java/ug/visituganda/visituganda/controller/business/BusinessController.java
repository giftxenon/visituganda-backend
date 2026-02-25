package ug.visituganda.visituganda.controller.business;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ug.visituganda.visituganda.dto.request.CreateBusinessRequest;
import ug.visituganda.visituganda.dto.response.BusinessResponse;
import ug.visituganda.visituganda.entity.Business.BusinessRegister;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;
import ug.visituganda.visituganda.service.business.BusinessPostService;
import ug.visituganda.visituganda.service.business.BusinessRegisterService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessPostService service;
    private final BusinessRegisterService businessRegisterService;

    // =========================================
    // 1️⃣ BUSINESS REGISTER PROFILE
    // =========================================
    // NOTE: Security handled globally, endpoint permitted in SecurityConfig
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerBusiness(
            @RequestParam String companyName,
            @RequestParam BusinessCategory category,
            @RequestParam String location,
            @RequestParam String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String operatingHours,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) MultipartFile logo,
            Authentication authentication
    ) {
        try {
            String username = authentication.getName(); // authenticated user

            return ResponseEntity.ok(
                    businessRegisterService.registerBusiness(
                            companyName,
                            category,
                            location,
                            phone,
                            email,
                            operatingHours,
                            description,
                            logo,
                            username
                    )
            );

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + ex.getMessage());
        }
    }

    // =========================================
    // 2️⃣ BUSINESS CREATES POST (OPTIONAL FEATURE)
    // =========================================
    @PreAuthorize("hasRole('BUSINESS')")
    @PostMapping("/list")
    public ResponseEntity<BusinessResponse> create(
            @RequestParam Long ownerId,
            @RequestBody CreateBusinessRequest request
    ) {
        return ResponseEntity.ok(service.createBusiness(request, ownerId));
    }

    // =========================================
    // 3️⃣ CUSTOMER FETCHES BUSINESSES BY CATEGORY
    // =========================================
    @GetMapping
    public ResponseEntity<List<BusinessResponse>> getByCategory(
            @RequestParam BusinessCategory category
    ) {
        return ResponseEntity.ok(
                service.getBusinessesByCategory(category)
        );
    }

    // =========================================
    // 4️⃣ PUBLIC: FETCH ALL BUSINESSES
    // 🔥 ADDED TO FIX 403 ERROR FROM FRONTEND
    // 🔥 Used by CarRentalAllCompaniesList.jsx
    // =========================================
    @GetMapping("/all")
    public ResponseEntity<List<BusinessRegister>> getAllBusinesses() {
        return ResponseEntity.ok(
                businessRegisterService.getAllBusinesses()
        );
    }

    @GetMapping("/logo/{id}")
    public ResponseEntity<byte[]> getBusinessLogo(@PathVariable Long id) {

        BusinessRegister business =
                businessRegisterService.getBusinessById(id);

        if (business.getLogo() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // or PNG if you want
                .body(business.getLogo());
    }
}