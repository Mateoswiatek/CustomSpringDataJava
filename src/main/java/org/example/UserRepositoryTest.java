package org.example;

import org.example.model.adnotations.databaserepository.AbstractRepository;
import org.example.model.adnotations.databaserepository.RepoAutoImpl;

@RepoAutoImpl
public interface UserRepositoryTest extends AbstractRepository<User, Long> {
    void findnamesurnameage(String s, Long l);
    void findAll();
    void countnamesurname();
    void countAllUPname();
    void FROMAddressfindAll();
//    void FROMAddressfindstreet();
    void findnamesurnameFROMAddressfindAll();
    void findAllWhere();
//    void findWhereAll(); // powinien wywalić błąd
    void findAllWherenameIs();
    void findAllWherenameIsAnd();
    void findAllWheresurnameIsOr();

}
