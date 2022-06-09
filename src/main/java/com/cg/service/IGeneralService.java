package com.cg.service;

import java.util.List;

public interface IGeneralService<T> {

    List<T> findAll();

    T findById(int id);

    boolean save(T t);

    boolean update(T t);

    boolean remove(int id);

}
