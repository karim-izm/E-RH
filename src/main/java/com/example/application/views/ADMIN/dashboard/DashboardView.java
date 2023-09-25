package com.example.application.views.ADMIN.dashboard;


import com.example.application.models.Employee;
import com.example.application.service.AbsenceService;
import com.example.application.service.CongeService;
import com.example.application.service.EmployeeService;
import com.example.application.service.PrimeService;
import com.example.application.views.MainLayout;
import com.example.application.views.ADMIN.dashboard.ServiceHealth.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import java.util.List;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends Main {

    EmployeeService employeeService;
    private final CongeService congeService;

    private final AbsenceService absenceService;
    private final PrimeService primeService;


    public DashboardView(EmployeeService employeeService, CongeService congeService, AbsenceService absenceService, PrimeService primeService) {
        this.employeeService = employeeService;
        this.congeService = congeService;
        this.absenceService = absenceService;
        this.primeService = primeService;
        addClassName("dashboard-view");
        VerticalLayout absentEmployees = new VerticalLayout(new H3("Employés Absent Aujourhui") , createAbsentEmployeesGrid());
        VerticalLayout congeEmployees = new VerticalLayout(new H3("Employés En Congé") , createEmployeesInCongeGrid());
        HorizontalLayout hl = new HorizontalLayout(absentEmployees , congeEmployees);
        hl.setSpacing(true);
        Board board = new Board();
        board.addRow(createHighlight("Nombre d'employés"," "+employeeService.countEmployees() , VaadinIcon.USERS),
                createHighlight("Total des primes", primeService.getTotalMontant()+" DH" , VaadinIcon.MONEY),
                createHighlight("Employés Absent", " "+absenceService.employeesInAbsence() , VaadinIcon.BED),
                createHighlight("Employés En Congés", " "+congeService.employeesInConge() , VaadinIcon.CALENDAR_BRIEFCASE));
        add(board , hl);
    }

    private Component createHighlight(String title, String value, VaadinIcon icon) {
        H2 h2 = new H2(title);
        h2.addClassNames(FontWeight.NORMAL, Margin.NONE, TextColor.SECONDARY, FontSize.XSMALL);

        Span span = new Span(value);
        span.addClassNames(FontWeight.SEMIBOLD, FontSize.XXXLARGE);

        Icon iconComponent = icon.create();
        iconComponent.addClassNames(BoxSizing.BORDER, Padding.XSMALL);

        VerticalLayout layout = new VerticalLayout(iconComponent, h2, span);
        layout.addClassName(Padding.LARGE);
        layout.setPadding(false);
        layout.setSpacing(false);
        return layout;
    }




    private HorizontalLayout createHeader(String title, String subtitle) {
        H2 h2 = new H2(title);
        h2.addClassNames(FontSize.XLARGE, Margin.NONE);

        Span span = new Span(subtitle);
        span.addClassNames(TextColor.SECONDARY, FontSize.XSMALL);

        VerticalLayout column = new VerticalLayout(h2, span);
        column.setPadding(false);
        column.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout(column);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setSpacing(false);
        header.setWidthFull();
        return header;
    }

    private String getStatusDisplayName(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        if (status == Status.OK) {
            return "Ok";
        } else if (status == Status.FAILING) {
            return "Failing";
        } else if (status == Status.EXCELLENT) {
            return "Excellent";
        } else {
            return status.toString();
        }
    }

    private String getStatusTheme(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        String theme = "badge primary small";
        if (status == Status.EXCELLENT) {
            theme += " success";
        } else if (status == Status.FAILING) {
            theme += " error";
        }
        return theme;
    }

    private Grid<Employee> createAbsentEmployeesGrid() {
        List<Employee> absentEmployees = absenceService.getEmployeesInAbsenceToday();
        Grid<Employee> grid = new Grid<>();
        grid.setItems(absentEmployees);
        grid.addColumn(Employee::getFullName).setHeader("Employé");
        grid.addColumn(Employee::getCin).setHeader("CIN");
        return grid;
    }

    private Grid<Employee> createEmployeesInCongeGrid() {
        List<Employee> employeesInConge = congeService.getEmployeesOnLeaveToday();
        Grid<Employee> grid = new Grid<>();
        grid.setItems(employeesInConge);
        grid.addColumn(Employee::getFullName).setHeader("Employé");
        grid.addColumn(Employee::getCin).setHeader("CIN");
        return grid;
    }

}
