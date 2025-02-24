package dkhpweb.dkhp_backend.dtos;

public record Pagination(
    Integer currentPage,
    Integer totalPages,
    Long totalRecords
){}
