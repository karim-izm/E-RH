package com.example.application.views.ADMIN.gestion_conges;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

        import com.example.application.models.Conge;
        import com.example.application.models.Employee;
        import com.example.application.service.CongeService;
        import com.example.application.service.EmployeeService;
        import com.example.application.views.MainLayout;
        import com.vaadin.flow.component.button.Button;
        import com.vaadin.flow.component.grid.Grid;
        import com.vaadin.flow.component.grid.GridVariant;
        import com.vaadin.flow.component.html.H1;
        import com.vaadin.flow.component.notification.Notification;
        import com.vaadin.flow.component.notification.NotificationVariant;
        import com.vaadin.flow.component.orderedlayout.VerticalLayout;
        import com.vaadin.flow.router.PageTitle;
        import com.vaadin.flow.router.Route;

        import java.util.List;

@Route(value = "Demandes_Congés", layout = MainLayout.class)
@PageTitle("Demandes de Congés")
public class Demandes_Conges extends VerticalLayout {
    private final CongeService congeService;
    private final EmployeeService employeeService;

    private final Grid<Conge> congeGrid = new Grid<>(Conge.class);

    public Demandes_Conges(CongeService congeService, EmployeeService employeeService) {
        this.congeService = congeService;
        this.employeeService = employeeService;

        H1 title = new H1("Demandes de Congés");

        // Configure the Congé grid
        congeGrid.setColumns("dateOfEffect", "numberOfDays", "status");
        congeGrid.addColumn(conge -> conge.getEmployee().getFullName()).setHeader("Employé");
        congeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        congeGrid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_ROW_STRIPES);
        congeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        // Add a custom column for accepting/rejecting Congé requests
        congeGrid.addComponentColumn(conge -> {
            Button acceptButton = new Button("Accept");
            acceptButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            acceptButton.addClickListener(e -> handleAcceptCongeRequest(conge));
            return acceptButton;
        }).setHeader("Accept");

        congeGrid.addComponentColumn(conge -> {
            Button rejectButton = new Button("Reject");
            rejectButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            rejectButton.addClickListener(e -> handleRejectCongeRequest(conge));
            return rejectButton;
        }).setHeader("Reject");


        updateCongeGrid();

        add(title, congeGrid);
    }

    private void updateCongeGrid() {
        List<Conge> congeRequests = congeService.getAllCongeRequests();
        congeGrid.setItems(congeRequests);
    }

    private void handleAcceptCongeRequest(Conge conge) {
        conge.setStatus("Accepté");
        congeService.updateConge(conge);
        updateCongeGrid();
        Notification.show("Congé request accepted!", 3000, Notification.Position.TOP_STRETCH)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void handleRejectCongeRequest(Conge conge) {
        conge.setStatus("Rjeté");
        congeService.updateConge(conge);
        updateCongeGrid();
        Notification.show("Congé request rejected!", 3000, Notification.Position.TOP_STRETCH)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
