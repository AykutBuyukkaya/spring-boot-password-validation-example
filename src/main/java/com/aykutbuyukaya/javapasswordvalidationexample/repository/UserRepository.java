package com.aykutbuyukaya.javapasswordvalidationexample.repository;

import com.aykutbuyukaya.javapasswordvalidationexample.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
