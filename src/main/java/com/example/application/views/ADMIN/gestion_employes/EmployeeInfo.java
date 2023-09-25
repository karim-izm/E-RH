package com.example.application.views.ADMIN.gestion_employes;

import com.example.application.models.Employee;
import com.example.application.service.EmployeeService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.datepicker.DatePicker;

import java.time.LocalDate;

public class EmployeeInfo extends Dialog {

    EmployeeService employeeService;
    private final EmployeesList employeesList;

    private Employee employee;

    private final Button saveButton = new Button("Save");
    private final Button deleteButton = new Button("Delete");
    private final Button cancelButton = new Button("Cancel");

    public EmployeeInfo(Employee employee , EmployeeService employeeService, EmployeesList employeesList) {
        this.employeeService = employeeService;
        this.employee = employee;
        this.employeesList = employeesList;
        setWidth("auto");
        add(new H3("Employee Details"));

        Hr hr = new Hr();

        VerticalLayout layout = new VerticalLayout();
        layout.getStyle().set("padding", "1em");
        layout.getStyle().set("margin", "1em");
        layout.getStyle().set("border", "1px solid #ccc");
        layout.getStyle().set("border-radius", "5px");


        HorizontalLayout row1 = new HorizontalLayout(
                createTextField("CIN", employee.getCin()),
                createTextField("Nom Complet", employee.getFirstName() + " " + employee.getLastName()),
                createDateField("Date of Birth", employee.getBirthDate()),
                createTextField("Sexe", employee.getGender())
        );
        row1.setSpacing(true);

        HorizontalLayout row2 = new HorizontalLayout(
                createTextField("Email", employee.getEmail()),
                createTextField("Telephone", employee.getTele())

        );
        row2.setSpacing(true);

        HorizontalLayout row3 = new HorizontalLayout(
                createDateField("Date d'embauche", employee.getDateEmbauche()),
                createTextField("Date debut de contrat", employee.getDateDebutCt()),
                createTextField("Date de fin de contrat", employee.getDateFinCt()),
                createTextField("Departement", employee.getDepartement())
        );
        row3.setSpacing(true);

        HorizontalLayout row4 = new HorizontalLayout(
                createTextField("Address", employee.getAdresse()),
                createTextField("Situation familiale", employee.getSituation_familiale()),
                createTextField("Nombre d'enfants", String.valueOf(employee.getNb_enfants()))
        );
        row4.setSpacing(true);

        HorizontalLayout row5 = new HorizontalLayout(
                createTextField("Nombre de congés", String.valueOf(employee.getNbr_conje()))
        );

        layout.add(row1, hr , row2, hr ,  row3, hr ,  row4, hr ,  row5);
        add(layout , createButtonLayout());
    }

    private TextField createTextField(String label, String value) {
        TextField textField = new TextField(label);
        textField.setValue(value);
        return textField;
    }

    private DatePicker createDateField(String label, String value) {
        DatePicker datePicker = new DatePicker(label);
        datePicker.setValue(LocalDate.parse(value));
        datePicker.setReadOnly(true);
        return datePicker;
    }

    private Component createButtonLayout() {
        saveButton.addClickListener(e -> onSaveButtonClick());
        deleteButton.addClickListener(e -> onDeleteButtonClick());
        cancelButton.addClickListener(e -> onCancelButtonClick());

        return new HorizontalLayout(saveButton, deleteButton, cancelButton);
    }

    private void onSaveButtonClick() {
        // Implement save action here
        close();
    }

    private void onDeleteButtonClick() {
        try{
            employeeService.deleteEmployee(employee);
            Notification.show("Employé suprimé avec success ! " , 3000  , Notification.Position.TOP_STRETCH).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            employeesList.updateList();
        }catch (Exception exception){
            Notification.show("Error deleting the employee ! " , 3000  , Notification.Position.TOP_STRETCH).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }


        close();
    }

    private void onCancelButtonClick() {
        close();
    }
}
