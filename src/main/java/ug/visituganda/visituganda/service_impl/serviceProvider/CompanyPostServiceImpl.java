package ug.visituganda.visituganda.service_impl.serviceProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.dto.request.CreateCompanyRequest;
import ug.visituganda.visituganda.dto.response.BusinessResponse;
import ug.visituganda.visituganda.entity.company.CompanyPost;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;
import ug.visituganda.visituganda.repository.CompanyRepository;
import ug.visituganda.visituganda.repository.UserRepository;
import ug.visituganda.visituganda.service.serviceProvider.CompanyPostService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyPostServiceImpl implements CompanyPostService {

    private final CompanyRepository businessRepository;
    private final UserRepository userRepository;

    @Override
    public BusinessResponse createBusiness(
            CreateCompanyRequest request,
            Long ownerId
    ) {
        // Fetch the user
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure only BUSINESS type users can add businesses
        if (owner.getUserType() != null && !owner.getUserType().name().equals("BUSINESS")) {
            throw new RuntimeException("Only BUSINESS users can add businesses");
        }

        // Create CompanyPost using record accessors
        CompanyPost business = CompanyPost.builder()
                .name(request.companyName())         // use parentheses
                .image(request.companyName())       // use parentheses
                .location(request.location())
                .category(request.category())
                .rating(0.0)
                .owner(owner)
                .build();
        // Save and map to response
        CompanyPost savedBusiness = businessRepository.save(business);
        return mapToResponse(savedBusiness);
    }

    @Override
    public List<BusinessResponse> getBusinessesByCategory(BusinessCategory category) {
        return businessRepository.findByCategory(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Helper to map entity to response
    private BusinessResponse mapToResponse(CompanyPost business) {
        return new BusinessResponse(
                business.getId(),
                business.getName(),
                business.getRating(),
                business.getImage(),
                business.getLocation()
        );
    }
}