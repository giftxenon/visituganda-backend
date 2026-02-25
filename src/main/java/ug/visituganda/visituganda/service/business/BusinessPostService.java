package ug.visituganda.visituganda.service.business;


import ug.visituganda.visituganda.dto.request.CreateBusinessRequest;
import ug.visituganda.visituganda.dto.response.BusinessResponse;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;

import java.util.List;

public interface BusinessPostService {

    BusinessResponse createBusiness(
            CreateBusinessRequest request,
            Long ownerId
    );

    List<BusinessResponse> getBusinessesByCategory(
            BusinessCategory category
    );
}