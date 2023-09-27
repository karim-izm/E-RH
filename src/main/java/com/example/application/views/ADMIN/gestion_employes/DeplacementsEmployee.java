package com.example.application.views.ADMIN.gestion_employes;

import com.example.application.models.Deplacements;
import com.example.application.models.Employee;
import com.example.application.models.User;
import com.example.application.repositories.EmployeeRepo;
import com.example.application.service.DeplacementService;
import com.example.application.service.EmployeeService;
import com.example.application.views.EmployeeLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;

@Route(value = "deplacementEmployee" , layout = EmployeeLayout.class)
@PageTitle("Deplacements")

public class DeplacementsEmployee extends VerticalLayout {
    private final DeplacementService deplacementService;
    private final EmployeeService employeeService;

    private final EmployeeRepo employeeRepo;

    Employee currentEmployee;

    private final Grid<Deplacements> deplacementGrid = new Grid<>(Deplacements.class);

    public DeplacementsEmployee(DeplacementService deplacementService, EmployeeService employeeService, EmployeeRepo employeeRepo) {
        this.deplacementService = deplacementService;
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;

        User currentUser = (User) VaadinSession.getCurrent().getAttribute("user");
        currentEmployee = this.employeeRepo.findByUser(currentUser);
        H1 title = new H1("Vos Deplacements : "+currentEmployee.getFullName());


        deplacementGrid.setColumns("dateDebut", "dateFin", "destination");
        deplacementGrid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_ROW_STRIPES);
        deplacementGrid.setSelectionMode(Grid.SelectionMode.SINGLE);


        updatedeplacementGrid();

        add(title, deplacementGrid);
    }

    private void updatedeplacementGrid() {
        List<Deplacements> congeRequests = deplacementService.getByEmployee(currentEmployee);
        deplacementGrid.setItems(congeRequests);
    }
}
