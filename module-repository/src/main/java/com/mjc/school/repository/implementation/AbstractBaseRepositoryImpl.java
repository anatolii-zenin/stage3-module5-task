package com.mjc.school.repository.implementation;

import com.mjc.school.repository.PaginationCapableRepository;
import com.mjc.school.repository.model.BaseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


@Repository
public abstract class AbstractBaseRepositoryImpl<T extends BaseEntity<Long>> implements PaginationCapableRepository<T, Long> {
    @Override
    public List<T> readAll() {
        var findAll = getEntityManager().createQuery(
            "SELECT a FROM " + getTableName() + " a", getEntityClass());
        return findAll.getResultList();
    }

    @Override
    public List<T> readAll(int page, int size) {
        var getPageOfEntries = getEntityManager().createQuery(
            "SELECT a FROM " + getTableName() + " a ", getEntityClass());
        getPageOfEntries.setMaxResults(size);
        getPageOfEntries.setFirstResult((page - 1)*size);
        return getPageOfEntries.getResultList();
    }

    @Override
    public Optional<T> readById(Long id) {
        return Optional.ofNullable(getEntityManager().find(getEntityClass(), id));
    }

    @Override
    public T create(T entity) {
        return merge(entity);
    }

    @Override
    public T update(T entity) {
        return merge(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        var obj = readById(id);
        if (obj.isPresent())
            getEntityManager().remove(obj.get());
        else
            return false;
        return readById(id).isEmpty();
    }

    @Override
    public boolean existById(Long id) {
        Optional<T> itemById = readById(id);
        return itemById.isPresent();
    }

    protected abstract Class<T> getEntityClass();
    protected abstract EntityManager getEntityManager();
    protected abstract String getTableName();

    private T merge(T entity) {
        var mergedEntity = getEntityManager().merge(entity);
        return getEntityManager().find(getEntityClass(), mergedEntity.getId());
    }
}
