package com.yeshtery.picturepublishing.repository;

import com.yeshtery.picturepublishing.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

}
