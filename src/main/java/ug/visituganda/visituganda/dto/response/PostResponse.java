package ug.visituganda.visituganda.dto.response;

public record PostResponse(
        Long id,
        String title,
        String date,
        String description,
        String imageBase64
) {}