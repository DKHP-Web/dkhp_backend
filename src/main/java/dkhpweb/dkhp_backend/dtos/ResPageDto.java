package dkhpweb.dkhp_backend.dtos;

import java.util.List;

public record ResPageDto<T>(
        List<T> data,
        Pagination pagination
) {}
