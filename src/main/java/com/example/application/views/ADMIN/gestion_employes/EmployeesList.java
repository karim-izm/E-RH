package com.example.application.views.ADMIN.gestion_employes;

import com.example.application.models.Employee;
import com.example.application.service.EmployeeService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

import java.util.List;

@Route(value = "employees" , layout = MainLayout.class)
@PageTitle("List des employés")
public class EmployeesList extends VerticalLayout {

    private final EmployeeService employeeService;

    private TextField searchField = new TextField();

    private Grid<Employee> grid = new Grid<>();;

    public EmployeesList(EmployeeService employeeService) {
        this.employeeService = employeeService;
        addClassName("employees-list");
        H3 title = new H3("Liste des employés");
        title.addClassName("title");
        Button addButton = new Button("Ajouter Un Employé", new Icon(VaadinIcon.PLUS));
        searchField.setPlaceholder("Rechercher par CIN ...");
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        addButton.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(AddEmployeeForm.class);
        });
        HorizontalLayout addAndSearch = new HorizontalLayout(addButton , searchField);
        addAndSearch.setJustifyContentMode(JustifyContentMode.BETWEEN);
        addAndSearch.setAlignItems(FlexComponent.Alignment.BASELINE);
        addAndSearch.setWidthFull();
        add(title , addAndSearch);

        grid.addItemClickListener(event -> {
            Employee selectedEmployee = event.getItem();
            openEmployeePopup(selectedEmployee);
        });


        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            String cin = e.getValue();
            searchByCIN(cin);
        });
        searchField.setClearButtonVisible(true);


        configureGrid();
        add(grid);
    }

    public void searchByCIN(String cin) {
        if (cin.isEmpty()) {
            // If the search field is empty, show all records
            grid.setItems(employeeService.getAllEmployees());
        } else {
            // Perform a partial match search
            List<Employee> searchResults = employeeService.searchByCIN(cin);
            grid.setItems(searchResults);
        }
    }


    private void openEmployeePopup(Employee employee) {
        EmployeeInfo popupForm = new EmployeeInfo(employee , employeeService, this);
        popupForm.open();
    }



    public void configureGrid(){
        grid.setItems(employeeService.getAllEmployees());

        grid.addColumn(Employee::getCin).setHeader("CIN");
        grid.addColumn(Employee::getFirstName).setHeader("Prénom");
        grid.addColumn(Employee::getLastName).setHeader("Nom");
        grid.addColumn(Employee::getGender).setHeader("Genre");
        grid.addColumn(Employee::getBirthDate).setHeader("Date de naissance");
        grid.addColumn(Employee::getEmail).setHeader("Email");
        grid.addColumn(Employee::getTele).setHeader("Téléphone");
        grid.addColumn(Employee::getSalary).setHeader("Salaire");
        grid.addColumn(Employee::getDateEmbauche).setHeader("Date d'embauche");
        grid.addColumn(Employee::getDateDebutCt).setHeader("Date de début de contrat");
        grid.addColumn(Employee::getDateFinCt).setHeader("Date de fin de contrat");
        grid.addColumn(Employee::getDepartement).setHeader("Service");
        grid.addColumn(Employee::getAdresse).setHeader("Adresse");
        grid.addColumn(Employee::getSituation_familiale).setHeader("Situation familiale");
        grid.addColumn(Employee::getNb_enfants).setHeader("Nombre d'enfants");
        grid.addColumn(Employee::getNbr_conje).setHeader("Nombre de congés");



        grid.setWidthFull();
        grid.setHeight("500px");
        grid.getStyle().set("overflow-y", "auto");
        grid.getStyle().set("scrollbar-width", "thin");
        grid.getStyle().set("scrollbar-color", "#888 #f4f4f4");
        grid.setVisible(true);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    }

    public void updateList(){
        grid.setItems(employeeService.getAllEmployees());
    }
}
