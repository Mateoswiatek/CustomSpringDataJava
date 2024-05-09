package org.example;


import org.example.model.adnotations.databaserepository.AbstractRepository;
import org.example.model.adnotations.databaserepository.RepoAutoImpl;

@RepoAutoImpl
public interface UserRepository extends AbstractRepository<User, Long> {
    User findAll();
}
