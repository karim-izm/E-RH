package com.example.application.views.ADMIN.gestion_primes;

import com.example.application.models.Employee;
import com.example.application.models.Prime;
import com.example.application.service.EmployeeService;
import com.example.application.service.PrimeService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "ajouterPrime", layout = MainLayout.class)
@PageTitle("Add Prime")
public class AddPrime extends VerticalLayout {
    private final EmployeeService employeeService;
    private final PrimeService primeService;

    private final ComboBox<Employee> employeeComboBox = new ComboBox<>("Employé");
    private final TextField montantField = new TextField("Montant");
    private final DatePicker dateField = new DatePicker("Date");
    private final TextField motifField = new TextField("Motif");

    private final Button addButton = new Button("Ajouter");

    public AddPrime(EmployeeService employeeService, PrimeService primeService) {
        this.employeeService = employeeService;
        this.primeService = primeService;

        List<Employee> employees = employeeService.getAllEmployees();
        employeeComboBox.setItems(employees);
        employeeComboBox.setItemLabelGenerator(Employee::getFullName);

        addButton.addClickListener(e -> addPrime());

        FormLayout formLayout = new FormLayout(employeeComboBox, montantField, dateField, motifField, addButton);
        add(formLayout);
    }

    private void addPrime() {
        Prime prime = new Prime();
        prime.setEmployee(employeeComboBox.getValue());
        prime.setMontant(Double.parseDouble(montantField.getValue()));
        prime.setDate(dateField.getValue());
        prime.setMotif(motifField.getValue());

        primeService.addPrime(prime);
        clearForm();
        Notification.show("Prime ajoutée ! ", 3000, Notification.Position.TOP_STRETCH)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public void clearForm() {
        montantField.clear();
        dateField.clear();
        motifField.clear();
        employeeComboBox.clear();
    }
}
