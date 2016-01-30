package com.summercoding.zooplus.repository;

import com.summercoding.zooplus.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String username);
}
