package ug.visituganda.visituganda.service.serviceProvider;


import ug.visituganda.visituganda.dto.request.CreateCompanyRequest;
import ug.visituganda.visituganda.dto.response.BusinessResponse;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;

import java.util.List;

public interface CompanyPostService {

    BusinessResponse createBusiness(
            CreateCompanyRequest request,
            Long ownerId
    );

    List<BusinessResponse> getBusinessesByCategory(
            BusinessCategory category
    );
}