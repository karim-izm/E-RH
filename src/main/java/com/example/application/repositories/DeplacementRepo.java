package com.example.application.repositories;

import com.example.application.models.Conge;
import com.example.application.models.Deplacements;
import com.example.application.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeplacementRepo extends JpaRepository<Deplacements , Long>{
    @Query("SELECT d FROM Deplacements d WHERE d.employee = :employee")
    List<Deplacements> getDeplacementsByEmployees(Employee employee);

}
