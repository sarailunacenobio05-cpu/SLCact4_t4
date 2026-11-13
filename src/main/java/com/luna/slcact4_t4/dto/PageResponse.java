package com.luna.slcact4_t4.dto;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last) {

    public static <E, T> PageResponse<T> from(Page<E> p, Function<E, T> mapper) {
        return new PageResponse<>(
                p.getContent().stream().map(mapper).toList(),
                p.getNumber(),
                p.getSize(),
                p.getTotalElements(),
                p.getTotalPages(),
                p.isLast());
    }
}
