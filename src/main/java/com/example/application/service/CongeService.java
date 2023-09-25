package com.example.application.service;

import com.example.application.models.Conge;
import com.example.application.models.Employee;
import com.example.application.repositories.CongeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongeService {
    CongeRepo congeRepo;

    public CongeService(CongeRepo congeRepo){
        this.congeRepo = congeRepo;
    }

    public void demanderConge(Conge c){
        congeRepo.save(c);
    }

    public List<Conge> getAllCongeRequests() {
        return congeRepo.findByStatus("En Attente");
    }

    public void updateConge(Conge conge) {
        Conge existingConge = congeRepo.findById(conge.getId()).orElse(null);

        if (existingConge != null) {
            // Modify the attributes of the Congé entity
            existingConge.setStatus(conge.getStatus());

            // Save the updated Congé entity back to the repository
            congeRepo.save(existingConge);
        }
    }

    public List<Conge> getAll() {
        return congeRepo.findAll();
    }

    public int employeesInConge(){
        return congeRepo.countEmployeesOnCongeToday();
    }

    public List<Conge> getCongeDemandsForEmployee(Employee employee) {
        return congeRepo.getCongeDemandsForEmployee(employee);
    }

    public List<Employee> getEmployeesOnLeaveToday() {
        return congeRepo.findEmployeesOnLeaveToday();
    }
}
