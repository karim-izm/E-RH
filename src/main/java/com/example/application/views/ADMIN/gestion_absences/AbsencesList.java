package com.example.application.views.ADMIN.gestion_absences;

import com.example.application.models.Absence;
import com.example.application.service.AbsenceService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "absences", layout = MainLayout.class)
@PageTitle("Liste des Absences")
public class AbsencesList extends VerticalLayout {

    private final AbsenceService absenceService;
    private final Grid<Absence> grid = new Grid<>(Absence.class);

    public AbsencesList(AbsenceService absenceService) {
        this.absenceService = absenceService;
        configureGrid();

        add(grid);
        updateGrid();
    }

    private void configureGrid() {
        grid.setColumns("employee.fullName", "dateDebut", "dateFin", "numberOfDays", "justification");
        grid.getColumnByKey("employee.fullName").setHeader("Employé");
        grid.getColumnByKey("dateDebut").setHeader("Date de Début");
        grid.getColumnByKey("dateFin").setHeader("Date de Fin");
        grid.getColumnByKey("numberOfDays").setHeader("Nombre de Jours");
        grid.getColumnByKey("justification").setHeader("Justification");

        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

    private void updateGrid() {
        grid.setItems(absenceService.getAllAbsences());
    }
}
