package com.bianchunguang.blog.persistence.services;

import java.io.Serializable;
import java.util.List;

public interface AbstractService<T, ID extends Serializable> {

    <S extends T> S save(S entity);

    <S extends T> Iterable<S> save(Iterable<S> entities);

    T findOne(ID id);

    List<T> findAll();

    void delete(ID id);

    void deleteAll();
}
