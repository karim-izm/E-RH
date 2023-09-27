package com.example.application.views.ADMIN.gestion_deplacements;

import com.example.application.models.Conge;
import com.example.application.models.Deplacements;
import com.example.application.service.CongeService;
import com.example.application.service.DeplacementService;
import com.example.application.service.EmployeeService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "deplacements" , layout = MainLayout.class)
public class ListDeplacements extends VerticalLayout {
    private final DeplacementService deplacementService;
    private final EmployeeService employeeService;

    private final Grid<Deplacements> congeGrid = new Grid<>(Deplacements.class);

    public ListDeplacements(DeplacementService deplacementService, EmployeeService employeeService) {
        this.deplacementService = deplacementService;
        this.employeeService = employeeService;

        H1 title = new H1("Historiques des Deplacements");

        // Configure the Congé grid
        congeGrid.setColumns("dateDebut", "dateFin", "destination");
        congeGrid.addColumn(deplacements -> deplacements.getEmployee().getFullName()).setHeader("Employé");
        congeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        congeGrid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_ROW_STRIPES);
        congeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);


        updateCongeGrid();

        add(title, congeGrid);
    }

    private void updateCongeGrid() {
        List<Deplacements> congeRequests = deplacementService.getAll();
        congeGrid.setItems(congeRequests);
    }
}
