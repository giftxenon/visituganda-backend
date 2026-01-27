package ug.visituganda.visituganda.repository;


import org.springframework.data.repository.CrudRepository;
//import org.springframework.security.core.userdetails.UserModel;
import ug.visituganda.visituganda.entity.User;
import org.springframework.stereotype.Repository;



import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

   Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByMsisdn(String msisdn);

}

