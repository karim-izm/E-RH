package com.example.application.repositories;

import com.example.application.models.Employee;
import com.example.application.models.Prime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrimeRepo extends JpaRepository<Prime, Long> {
    List<Prime> getPrimesByEmployee(Employee employee);

    List<Prime> findByEmployee(Employee employee);

    @Query("SELECT SUM(p.montant) FROM Prime p")
    Double getTotalMontant();
}
