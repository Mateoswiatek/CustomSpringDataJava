package org.example;

import org.example.adnotations.databaserepository.AbstractRepository;
import org.example.adnotations.databaserepository.RepoAutoImpl;

@RepoAutoImpl
public interface UserRepositoryTest extends AbstractRepository<User, Long> {
    User findAll();
    void method1();
    String method2();
    Integer method3();
}
