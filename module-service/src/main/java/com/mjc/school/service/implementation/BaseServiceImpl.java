package com.mjc.school.service.implementation;

import com.mjc.school.repository.PaginationCapableRepository;
import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.service.PaginationCapableService;
import com.mjc.school.service.dto.Request;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.validator.annotations.Validate;
import com.mjc.school.service.validator.annotations.ValidateUpdate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class BaseServiceImpl<Req extends Request<Long>, Resp, Entity extends BaseEntity<Long>,
        Repository extends PaginationCapableRepository<Entity, Long>>
        implements PaginationCapableService<Req, Resp, Long> {

    @Override
    public List<Resp> readAll() {
        return entitiesToDtos(getRepo().readAll());
    }

    @Override
    public List<Resp> readAll(int page, int size) {
        return entitiesToDtos(getRepo().readAll(page, size));
    }

    @Override
    public Resp readById(Long id) {
        var item = getRepo().readById(id);
        if(item.isEmpty())
            throw new NotFoundException("entity with id " + id + " does not exist.\n");
        return entityToDto(item.get());
    }

    @Override
    public Resp create(@Validate Req createRequest) {
        return entityToDto(
                getRepo().create(dtoToEntity(createRequest))
        );
    }

    @Override
    public Resp update(@ValidateUpdate Req updateRequest) {
        var id = updateRequest.getId();
        var entityToUpdate = getRepo().readById(id);
        if (entityToUpdate.isEmpty())
            throw new RuntimeException("entry with ID " + id + " does not exist.");
        var entity = entityToUpdate.get();

        updateEntityFromDto(updateRequest, entity);
        return entityToDto(
                getRepo().update(entity)
        );
    }

    @Override
    public boolean deleteById(Long id) {
        return getRepo().deleteById(id);
    }

    protected abstract Entity dtoToEntity(Req dto);

    protected abstract List<Resp> entitiesToDtos(List<Entity> entities);

    protected abstract Resp entityToDto(Entity entity);
    
    protected abstract Repository getRepo();

    protected abstract void updateEntityFromDto(Req req, Entity entity);
}
