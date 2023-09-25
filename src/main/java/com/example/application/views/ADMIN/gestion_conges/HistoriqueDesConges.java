package com.example.application.views.ADMIN.gestion_conges;

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

@Route(value = "HistoriqueConges", layout = MainLayout.class)
@PageTitle("Historique des conges")
public class HistoriqueDesConges extends VerticalLayout {
    private final CongeService congeService;
    private final EmployeeService employeeService;

    private final Grid<Conge> congeGrid = new Grid<>(Conge.class);

    public HistoriqueDesConges(CongeService congeService, EmployeeService employeeService) {
        this.congeService = congeService;
        this.employeeService = employeeService;

        H1 title = new H1("Historiques des Congés");

        // Configure the Congé grid
        congeGrid.setColumns("dateOfEffect", "numberOfDays", "status");
        congeGrid.addColumn(conge -> conge.getEmployee().getFullName()).setHeader("Employé");
        congeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        congeGrid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_ROW_STRIPES);
        congeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);


        updateCongeGrid();

        add(title, congeGrid);
    }

    private void updateCongeGrid() {
        List<Conge> congeRequests = congeService.getAll();
        congeGrid.setItems(congeRequests);
    }
}
