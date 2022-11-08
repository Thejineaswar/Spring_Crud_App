package com.example.basiccrudpostgrespring.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T>{
    List<T> list();

    int create(T t);

    Optional<T> get(int aid);

    int update(T t, int id);

    int delete(int id);
}
