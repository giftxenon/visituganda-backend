package ug.visituganda.visituganda.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record PostRequest(
        String title,
        String date,          // will parse into LocalDate
        String description,
        MultipartFile image
) {}