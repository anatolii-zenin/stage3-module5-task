package com.mjc.school.repository;

import com.mjc.school.repository.model.BaseEntity;

import java.util.List;

public interface PaginationCapableRepository<T extends BaseEntity<K>, K> extends BaseRepository<T, K> {
    List<T> readAll(int page, int size);
}
