package com.example.application.service;

import com.example.application.models.Conge;
import com.example.application.models.Deplacements;
import com.example.application.models.Employee;
import com.example.application.repositories.DeplacementRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeplacementService {
    private final DeplacementRepo deplacementRepo;
    public DeplacementService(DeplacementRepo deplacementRepo1){
        this.deplacementRepo = deplacementRepo1;
    }

    public void add(Deplacements d){
        this.deplacementRepo.save(d);
    }

    public List<Deplacements> getAll() {
        return deplacementRepo.findAll();
    }

    public List<Deplacements> getByEmployee(Employee e ){
        return  deplacementRepo.getDeplacementsByEmployees(e);
    }
}
