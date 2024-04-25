package org.example.adnotations.databaserepository;


public interface AbstractRepository<T, ID> {
    T findAll();
    T findById(ID id);
    T save(T t);
}
