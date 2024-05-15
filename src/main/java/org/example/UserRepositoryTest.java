package org.example;

import org.example.model.adnotations.databaserepository.AbstractRepository;
import org.example.model.adnotations.databaserepository.RepoAutoImpl;

@RepoAutoImpl
public interface UserRepositoryTest extends AbstractRepository<User, Long> {
    void findname(String s, Long l);
}
