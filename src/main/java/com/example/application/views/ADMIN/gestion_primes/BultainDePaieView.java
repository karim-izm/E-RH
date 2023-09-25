package com.example.application.views.ADMIN.gestion_primes;

import com.example.application.models.Employee;
import com.example.application.models.Prime;
import com.example.application.service.EmployeeService;
import com.example.application.service.PrimeService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "bulteinDePaie", layout = MainLayout.class)
@PageTitle("Bulletin de Paie")
public class BultainDePaieView extends VerticalLayout {
    private final EmployeeService employeeService;
    private final PrimeService primeService;

    private final Grid<Employee> employeeGrid = new Grid<>(Employee.class);
    private final TextField searchField = new TextField("Rechercher par CIN ...");

    public BultainDePaieView(EmployeeService employeeService, PrimeService primeService) {
        this.employeeService = employeeService;
        this.primeService = primeService;

        H1 title = new H1("Bulletin de Paie");
        searchField.setWidth("100%");
        searchField.setPlaceholder("Rechercher par CIN ...");
        searchField.addValueChangeListener(e -> searchByCIN(e.getValue()));

        configureEmployeeGrid();
        updateEmployeeGrid();

        add(title, searchField, employeeGrid);
    }

    private void configureEmployeeGrid() {
        employeeGrid.setColumns("cin", "firstName", "lastName");
        employeeGrid.addColumn("fullName").setHeader("Nom Complet");
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        employeeGrid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_ROW_STRIPES);
        employeeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        // Add a selection listener to display the primes when a row is clicked
        employeeGrid.addSelectionListener(event -> {
            Employee selectedEmployee = event.getFirstSelectedItem().orElse(null);
            if (selectedEmployee != null) {
                showPrimesPopup(selectedEmployee);
            }
        });
    }

    private void updateEmployeeGrid() {
        List<Employee> employees = employeeService.getAllEmployees();
        employeeGrid.setItems(employees);
    }

    private void searchByCIN(String cin) {
        if (cin.isEmpty()) {
            updateEmployeeGrid();
        } else {
            List<Employee> searchResults = employeeService.searchByCIN(cin);
            employeeGrid.setItems(searchResults);
        }
    }

    private void showPrimesPopup(Employee employee) {
        List<Prime> primes = primeService.getPrimesByEmployee(employee);

        PrimesPopupView primesPopupView = new PrimesPopupView(primes , employee);
        primesPopupView.open();
    }

    private class PrimesPopupView extends Dialog {
        public PrimesPopupView(List<Prime> primes , Employee employee) {
            // Create the layout to display primes here
            // You can use a VerticalLayout and add labels or any other components to display primes
            VerticalLayout layout = new VerticalLayout();
            layout.getStyle().set("padding", "1em");
            layout.getStyle().set("margin", "1em");
            layout.getStyle().set("border", "1px solid #ccc");
            layout.getStyle().set("border-radius", "5px");
            Text info = new Text(employee.getFullName() + " | CIN : "+employee.getCin());
            H3 title = new H3("Bultain De Paie");

            for (Prime prime : primes) {
                String primeInfo = "- "+prime.getDate() + " | +" + prime.getMontant() + "DH : " + prime.getMotif();
                Paragraph primeLabel = new Paragraph(primeInfo);

                // Add the label to the layout
                layout.add(primeLabel);
            }

            add(title , info ,  layout);
        }
    }
}
