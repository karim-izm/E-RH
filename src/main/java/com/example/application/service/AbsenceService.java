package com.example.application.service;


import com.example.application.models.Absence;
import com.example.application.models.Employee;
import com.example.application.repositories.AbsenceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsenceService {
    AbsenceRepo absenceRepo ;

    AbsenceService(AbsenceRepo absenceRepo){
        this.absenceRepo = absenceRepo;
    }

    public void addAbsence(Absence a){
        absenceRepo.save(a);
    }

    public List<Absence> getAllAbsences() {
        return absenceRepo.findAll();
    }

    public int employeesInAbsence(){
        return absenceRepo.countEmployeesInAbsenceToday();
    }

    public List<Employee> getEmployeesInAbsenceToday() {
        return absenceRepo.findEmployeesInAbsenceToday();
    }
}
