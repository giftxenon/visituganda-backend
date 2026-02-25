package ug.visituganda.visituganda.dto.response;

public record BusinessResponse(
        Long id,
        String name,
        Double rating,
        String image,
        String location
) {}