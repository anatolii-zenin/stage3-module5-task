package com.mjc.school.service;

import java.util.List;

public interface PaginationCapableService<T, R, K> {
    List<R> readAll(int page, int size);

    List<R> readAll();

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);
}
