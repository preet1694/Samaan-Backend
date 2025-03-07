package org.samaan.repositories;

import org.samaan.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
//    User findByname(String name);

    User save(User user);

    Optional<User> findById(String id);
}