package org.example;

import org.example.model.adnotations.databaserepository.AbstractRepository;
import org.example.model.adnotations.databaserepository.RepoAutoImpl;

@RepoAutoImpl
public interface UserRepositoryTest extends AbstractRepository<User, Long> {
    void findnamesurnameage(String s, Long l);
}
