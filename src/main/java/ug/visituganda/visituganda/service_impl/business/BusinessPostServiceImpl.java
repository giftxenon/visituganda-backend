package ug.visituganda.visituganda.service_impl.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.dto.request.CreateBusinessRequest;
import ug.visituganda.visituganda.dto.response.BusinessResponse;
import ug.visituganda.visituganda.entity.Business.BusinessPost;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;
import ug.visituganda.visituganda.repository.BusinessRepository;
import ug.visituganda.visituganda.repository.UserRepository;
import ug.visituganda.visituganda.service.business.BusinessPostService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessPostServiceImpl implements BusinessPostService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    @Override
    public BusinessResponse createBusiness(
            CreateBusinessRequest request,
            Long ownerId
    ) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!owner.getUserType().name().equals("BUSINESS")) {
            throw new RuntimeException("Only BUSINESS users can add businesses");
        }

        BusinessPost business = BusinessPost.builder()
                .name(request.name())
                .image(request.image())
                .location(request.location())
                .category(request.category())
                .rating(0.0)
                .owner(owner)
                .build();

        return map(businessRepository.save(business));
    }

    @Override
    public List<BusinessResponse> getBusinessesByCategory(
            BusinessCategory category
    ) {
        return businessRepository.findByCategory(category)
                .stream()
                .map(this::map)
                .toList();
    }

    private BusinessResponse map(BusinessPost b) {
        return new BusinessResponse(
                b.getId(),
                b.getName(),
                b.getRating(),
                b.getImage(),
                b.getLocation()
        );
    }
}