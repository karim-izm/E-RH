package com.example.application.views.Employe;

import com.example.application.models.Conge;
import com.example.application.models.Employee;
import com.example.application.models.User;
import com.example.application.repositories.EmployeeRepo;
import com.example.application.service.CongeService;
import com.example.application.views.EmployeeLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "DemandeConge", layout = EmployeeLayout.class)
@PageTitle("Demande Conge")
public class RequestCongeView extends VerticalLayout {
    private final TextField cin = new TextField("CIN");

    private final EmployeeRepo employeeRepo;

    private final CongeService congeService;

    private final TextField fullname = new TextField("Nom Complet");
    private final DatePicker dateOfEffect = new DatePicker("Date de debut");
    private final IntegerField numberOfDays = new IntegerField("Nombre de jouts");
    private final Button requestButton = new Button("Demander");

    Employee currentEmployee;

    public RequestCongeView(EmployeeRepo employeeRepo, CongeService congeService) {
        this.employeeRepo = employeeRepo;
        this.congeService = congeService;
        cin.setReadOnly(true);
        fullname.setReadOnly(true);
        dateOfEffect.setRequired(true);
        numberOfDays.setRequired(true);

        User currentUser  = (User) VaadinSession.getCurrent().getAttribute("user");
        currentEmployee = employeeRepo.findByUser(currentUser);
        cin.setValue(currentEmployee.getCin());
        fullname.setValue(currentEmployee.getFullName());

        HorizontalLayout hl = new HorizontalLayout(cin , fullname);



        requestButton.addClickListener(event -> onRequestButtonClick());

        add(hl ,  dateOfEffect, numberOfDays, requestButton);
    }

    private void onRequestButtonClick() {
        Conge c = new Conge();
        c.setEmployee(currentEmployee);
        c.setStatus("En Attente");
        c.setDateOfEffect(dateOfEffect.getValue());
        c.setNumberOfDays(numberOfDays.getValue());
        congeService.demanderConge(c);
        clearFields();
        Notification.show("Congé demandé!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public void clearFields(){
        dateOfEffect.clear();
        numberOfDays.clear();
    }
}
