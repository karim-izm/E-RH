package com.example.application.repositories;

import com.example.application.models.Absence;
import com.example.application.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AbsenceRepo extends JpaRepository<Absence , Long>{
    List<Absence> findByEmployee(Employee employee);

    @Query(value = "SELECT COUNT(DISTINCT a.employee_id) " +
            "FROM Absence a " +
            "WHERE a.date_debut <= CURRENT_DATE " +
            "AND a.date_fin >= CURRENT_DATE", nativeQuery = true)
    int countEmployeesInAbsenceToday();

    @Query("SELECT a.employee FROM Absence a WHERE a.dateDebut <= CURRENT_DATE AND a.dateFin >= CURRENT_DATE")
    List<Employee> findEmployeesInAbsenceToday();


}
