package ug.visituganda.visituganda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;

public record CreateCompanyRequest(
        @NotBlank(message = "Company name is required")
        String companyName,

        // This will be a base64 String or URL to handle image uploads
        String logo,

        @NotBlank(message = "Location is required")
        String location,

        @NotNull(message = "Business category is required")
        BusinessCategory category
) {}