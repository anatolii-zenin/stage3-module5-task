package com.mjc.school.service;

import com.mjc.school.service.dto.page.PageDTOResp;

public interface PaginationCapableService<T, R, K> {
    PageDTOResp<R> readAll(int page, int size, String sortBy, String order);

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);
}
