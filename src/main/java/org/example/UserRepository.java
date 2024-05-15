package org.example;


import org.example.model.adnotations.databaserepository.AbstractRepository;
import org.example.model.adnotations.databaserepository.RepoAutoImpl;

@RepoAutoImpl
//        log.info("Tokeny to: " + tokens.toString());
public interface UserRepository extends AbstractRepository<User, Long> {
    User findAll();
}
