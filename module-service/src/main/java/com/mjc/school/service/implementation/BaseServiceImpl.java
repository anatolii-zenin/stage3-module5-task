package com.mjc.school.service.implementation;

import com.mjc.school.repository.PaginationCapableRepository;
import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.page.Page;
import com.mjc.school.repository.page.PageParams;
import com.mjc.school.service.PaginationCapableService;
import com.mjc.school.service.dto.Request;
import com.mjc.school.service.dto.page.PageDTOResp;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.validator.annotations.Validate;
import com.mjc.school.service.validator.annotations.ValidateUpdate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseServiceImpl<Req extends Request<Long>, Resp, Entity extends BaseEntity<Long>,
        Repository extends PaginationCapableRepository<Entity, Long>>
        implements PaginationCapableService<Req, Resp, Long> {

    @Override
    @Transactional(readOnly = true)
    public PageDTOResp<Resp> readAll(int page, int size, String sortBy, String order) {
        var pageParams = new PageParams();
        pageParams.setPageNum(page);
        pageParams.setPageSize(size);
        pageParams.setSortedBy("id");
        pageParams.setAscending(true);
        if (sortBy!= null && !sortBy.isBlank())
            pageParams.setSortedBy(sortBy);
        if (sortBy!= null && !order.isBlank())
            pageParams.setAscending(order.equals("asc"));
        return pageToDto(getRepo().readAll(pageParams));
    }

    @Override
    @Transactional(readOnly = true)
    public Resp readById(Long id) {
        var item = getRepo().readById(id);
        if(item.isEmpty())
            throw new NotFoundException("entity with id " + id + " does not exist.\n");
        return entityToDto(item.get());
    }

    @Override
    @Transactional
    public Resp create(@Validate Req createRequest) {
        return entityToDto(
                getRepo().create(dtoToEntity(createRequest))
        );
    }

    @Override
    @Transactional
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
    @Transactional
    public boolean deleteById(Long id) {
        return getRepo().deleteById(id);
    }

    protected abstract Entity dtoToEntity(Req dto);

    protected abstract List<Resp> entitiesToDtos(List<Entity> entities);

    protected abstract Resp entityToDto(Entity entity);
    protected abstract PageDTOResp<Resp> pageToDto(Page<Entity> page);
    
    protected abstract Repository getRepo();

    protected abstract void updateEntityFromDto(Req req, Entity entity);
}
