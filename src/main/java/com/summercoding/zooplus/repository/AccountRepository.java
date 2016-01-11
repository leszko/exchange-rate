package com.summercoding.zooplus.repository;

import com.summercoding.zooplus.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByName(String username);
}
