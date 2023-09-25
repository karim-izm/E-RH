package com.example.application.service;


import com.example.application.models.Absence;
import com.example.application.models.Employee;
import com.example.application.models.Prime;
import com.example.application.models.User;
import com.example.application.repositories.AbsenceRepo;
import com.example.application.repositories.EmployeeRepo;
import com.example.application.repositories.PrimeRepo;
import com.example.application.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    EmployeeRepo employeeRepo;
    UserRepository userRepository;
    PrimeRepo primeRepo;
    AbsenceRepo absenceRepo;
    public EmployeeService(EmployeeRepo employeeRepo , UserRepository userRepository  ,PrimeRepo primeRepo , AbsenceRepo absenceRepo) {
        this.employeeRepo = employeeRepo;
        this.userRepository = userRepository;
        this.primeRepo = primeRepo;
        this.absenceRepo = absenceRepo;
    }

    public void addEmployee(Employee employee){
        employeeRepo.save(employee);
    }

    public long countEmployees(){
        return employeeRepo.count();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public List<Employee> searchByCIN(String cin) {
        return employeeRepo.searchByCin(cin);
    }

    public void deleteEmployee(Employee e) {
        try {
            User user = e.getUser();
            userRepository.delete(user);

            // Delete related Primes
            List<Prime> primes = primeRepo.findByEmployee(e);
            primeRepo.deleteAll(primes);

            // Delete related Absences
            List<Absence> absences = absenceRepo.findByEmployee(e);
            absenceRepo.deleteAll(absences);

            employeeRepo.delete(e);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}
