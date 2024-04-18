package org.example;


import org.example.adnotations.databaserepository.AbstractRepository;
import org.example.adnotations.databaserepository.AnnRepository;

@AnnRepository
public interface UserRepository extends AbstractRepository<User, Long> {
    User findAll();
}
