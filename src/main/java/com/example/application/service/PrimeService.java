package com.example.application.service;

import com.example.application.models.Employee;
import com.example.application.models.Prime;
import com.example.application.repositories.PrimeRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrimeService {

    PrimeRepo primeRepo;
    PrimeService(PrimeRepo primeRepo){
        this.primeRepo = primeRepo;
    }


    public void addPrime(Prime p){
        primeRepo.save(p);
    }

    public List<Prime> getPrimesByEmployee(Employee employee) {
        return primeRepo.getPrimesByEmployee(employee);
    }

    public Double getTotalMontant() {
        return primeRepo.getTotalMontant();
    }
}
