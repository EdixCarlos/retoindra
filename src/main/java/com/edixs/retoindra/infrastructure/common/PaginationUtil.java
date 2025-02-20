package com.edixs.retoindra.infrastructure.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class PaginationUtil {

    public static Pageable createPageable(int page, int size, String sort) {
        if (StringUtils.hasText(sort)) {
            String[] sortParams = sort.split(",");
            Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
            return PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        }
        return PageRequest.of(page, size);
    }
}