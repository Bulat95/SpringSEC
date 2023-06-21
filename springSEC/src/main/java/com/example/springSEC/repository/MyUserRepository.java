package com.example.springSEC.repository;

import com.example.springSEC.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
        MyUser findByLogin(String login);
}
