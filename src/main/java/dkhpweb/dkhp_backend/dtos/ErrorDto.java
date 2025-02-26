package dkhpweb.dkhp_backend.dtos;

import java.util.List;

public record ErrorDto(
        Integer statusCode,
        String title,
        List<String> errors
) {}
