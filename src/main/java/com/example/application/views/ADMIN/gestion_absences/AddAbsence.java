package com.example.application.views.ADMIN.gestion_absences;

import com.example.application.models.Absence;
import com.example.application.models.Employee;
import com.example.application.service.AbsenceService;
import com.example.application.service.EmployeeService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.List;


@Route(value = "ajouterAbsence", layout = MainLayout.class)
@PageTitle("Add Absence")
public class AddAbsence extends VerticalLayout {
    private final EmployeeService employeeService;
    private final AbsenceService absenceService;

    private final ComboBox<Employee> employeeComboBox = new ComboBox<>("Employé");
    private final DatePicker startdateField = new DatePicker("Date debut");
    private final DatePicker enddateField = new DatePicker("Date fin");
    private final TextField numberOfDaysField = new TextField("Nombre de jours");
    private final TextArea justificationField = new TextArea("Justification");

    private final Button addButton = new Button("Ajouter");


    public AddAbsence(EmployeeService employeeService, AbsenceService absenceService) {
        this.employeeService = employeeService;
        this.absenceService = absenceService;

        List<Employee> employees = employeeService.getAllEmployees();
        employeeComboBox.setItems(employees);
        employeeComboBox.setItemLabelGenerator(Employee::getFullName);

        numberOfDaysField.setReadOnly(true);


        startdateField.setValue(LocalDate.now());
        startdateField.setRequired(true);
        numberOfDaysField.setRequired(true);
        justificationField.setRequired(true);

        startdateField.addValueChangeListener(this::calculateNumberOfDays);
        enddateField.addValueChangeListener(this::calculateNumberOfDays);

        addButton.addClickListener(e -> addAbsence());

        // Create a FormLayout for the form fields
        FormLayout formLayout = new FormLayout(employeeComboBox, startdateField, enddateField, numberOfDaysField, justificationField, addButton);
        add(formLayout);
    }

    private void calculateNumberOfDays(HasValue.ValueChangeEvent<LocalDate> event) {
        LocalDate startDate = startdateField.getValue();
        LocalDate endDate = enddateField.getValue();

        if (startDate != null && endDate != null) {
            long days = startDate.until(endDate).getDays() + 1;
            numberOfDaysField.setValue(String.valueOf(days));
        } else {
            numberOfDaysField.clear();
        }
    }

    private void addAbsence() {
        Absence a = new Absence();
        a.setDateDebut(startdateField.getValue());
        a.setDateFin(enddateField.getValue());
        a.setJustification(justificationField.getValue());
        a.setNumberOfDays(Integer.parseInt(numberOfDaysField.getValue()));
        a.setEmployee(employeeComboBox.getValue());

        absenceService.addAbsence(a);
        clearForm();
        Notification.show("Absence ajouté ! " , 3000 , Notification.Position.TOP_STRETCH).addThemeVariants(NotificationVariant.LUMO_SUCCESS);

    }

    public void clearForm(){
        startdateField.clear();
        enddateField.clear();
        justificationField.clear();
        numberOfDaysField.clear();
        employeeComboBox.clear();
    }
}
