package com.example.application.repositories;

import com.example.application.models.Employee;
import com.example.application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee , Long > {

    @Query("SELECT e from Employee e WHERE LOWER(e.cin) like lower(CONCAT(('%'), :searchTerm , '%'))")
    List<Employee> searchByCin(@Param("searchTerm") String searchTerm);

    Employee findByUser(User user);

}
