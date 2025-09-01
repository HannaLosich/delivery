package com.solvd.delivery.dao;

import java.util.List;

public interface IBaseDAO<T> {
    void insert(T entity);
    T getById(long id);
    List<T> getAll();
    T update(T entity);
    void removeById(long id);
}
