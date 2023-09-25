package com.example.application.repositories;

import com.example.application.models.Employee;
import com.example.application.models.Prime;
import com.example.application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM User u WHERE u.Username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

}
