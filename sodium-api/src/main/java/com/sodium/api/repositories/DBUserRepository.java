package com.sodium.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sodium.api.models.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
    public DBUser findByUsername(String username);
}