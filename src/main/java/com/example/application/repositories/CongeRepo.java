package com.example.application.repositories;

import com.example.application.models.Conge;
import com.example.application.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongeRepo extends JpaRepository<Conge, Long> {

    List<Conge> findByStatus(String enAttente);

    @Query(value = "SELECT COUNT(DISTINCT c.employee_id) " +
            "FROM Conge c " +
            "WHERE c.date_of_effect <= CURRENT_DATE " +
            "AND c.number_of_days > 0", nativeQuery = true)
    int countEmployeesOnCongeToday();


    @Query("SELECT c FROM Conge c WHERE c.employee = :employee")
    List<Conge> getCongeDemandsForEmployee(Employee employee);

    @Query("SELECT c.employee FROM Conge c WHERE c.dateOfEffect <= CURRENT_DATE AND c.numberOfDays > 0")
    List<Employee> findEmployeesOnLeaveToday();

}
