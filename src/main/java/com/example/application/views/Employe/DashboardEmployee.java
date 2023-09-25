package com.example.application.views.Employe;

import com.example.application.models.Conge;
import com.example.application.models.Employee;
import com.example.application.models.User;
import com.example.application.repositories.EmployeeRepo;
import com.example.application.service.CongeService;
import com.example.application.views.EmployeeLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;


@Route(value = "DashboardEmployee" , layout = EmployeeLayout.class)
@PageTitle("Dashbaord Employé")
public class DashboardEmployee extends VerticalLayout {
    Employee currentEmployee;
    private final EmployeeRepo employeeRepo;
    private final CongeService congeService;
    private Div notificationsDiv;

    public DashboardEmployee(EmployeeRepo employeeRepo, CongeService congeService) {
        this.employeeRepo = employeeRepo;
        this.congeService = congeService;


        User currentUser = (User) VaadinSession.getCurrent().getAttribute("user");
        currentEmployee = employeeRepo.findByUser(currentUser);

        notificationsDiv = new Div();
        notificationsDiv.setVisible(false); // Initially hidden

        // Create a button to trigger displaying notifications
        Button showNotificationsButton = new Button("Afficher les notifications");
        showNotificationsButton.addClickListener(event -> showCongeNotifications());


        add(new H1("Bonjour " + currentEmployee.getFullName()));
        add(showNotificationsButton);
        add(notificationsDiv);
    }


    private void showCongeNotifications() {
        // Retrieve congé demands for the current employee
        List<Conge> congéDemands = congeService.getCongeDemandsForEmployee(currentEmployee);

        // Check if there are any notifications to display
        if (congéDemands.isEmpty()) {
            notificationsDiv.setVisible(false);
        } else {
            notificationsDiv.setVisible(true);
            notificationsDiv.removeAll(); // Clear previous notifications


            for (Conge congé : congéDemands) {
                String status = congé.getStatus();
                String textColor = status.equals("Accepté") ? "green" : "red";

                String notificationMessage = "Votre demande de congé pour le " +
                        congé.getDateOfEffect() + " a été <span style='color:" + textColor + ";'>" +
                        status + "</span> ";

                Paragraph notificationLabel = new Paragraph(notificationMessage);
                notificationLabel.getElement().setProperty("innerHTML", notificationMessage);
                notificationsDiv.add(notificationLabel);
            }
        }
    }

}
