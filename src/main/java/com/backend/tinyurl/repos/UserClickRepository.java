package com.backend.tinyurl.repos;

import com.backend.tinyurl.Modles.Casandra.*;
import org.springframework.data.cassandra.repository.*;


public interface UserClickRepository extends CassandraRepository<UserClick, UserClickKey> {
    @Query("SELECT * FROM userclick WHERE user_name=:userName")
    Iterable<UserClick> findByUserName(String userName);
}
