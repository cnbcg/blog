package com.bianchunguang.blog.persistence.services.impl;

import com.bianchunguang.blog.persistence.services.AbstractService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractServiceImpl<T, ID extends Serializable> implements AbstractService<T, ID> {

    @Override
    public <S extends T> S save(S entity) {
        return getRepository().save(entity);
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return getRepository().save(entities);
    }

    @Override
    public T findOne(ID id) {
        return getRepository().findOne(id);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public void delete(ID id) {
        getRepository().delete(id);
    }

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }

    protected abstract JpaRepository<T, ID> getRepository();

}
