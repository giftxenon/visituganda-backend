package ug.visituganda.visituganda.dto.request;


import ug.visituganda.visituganda.modal.enums.BusinessCategory;

public record CreateBusinessRequest(
        String name,
        String image,
        String location,
        BusinessCategory category
) {}