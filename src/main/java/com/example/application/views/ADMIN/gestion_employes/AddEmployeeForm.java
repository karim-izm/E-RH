package com.example.application.views.ADMIN.gestion_employes;

import com.example.application.models.Employee;
import com.example.application.models.User;
import com.example.application.service.AuthService;
import com.example.application.service.EmployeeService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;

@Route(value = "addEmployee", layout = MainLayout.class)
@PageTitle("Ajouter un employé")
public class AddEmployeeForm extends VerticalLayout {

    private final AuthService authService;

    private final EmployeeService employeeService;

    private TextField cinField = new TextField("CIN");
    private TextField firstNameField = new TextField("Prénom");
    private TextField lastNameField = new TextField("Nom");
    private Select<String> genderField = new Select<>();
    private DatePicker birthDateField = new DatePicker("Date de naissance");
    private TextField emailField = new TextField("Email");
    private TextField teleField = new TextField("Téléphone");
    private DatePicker dateEmbaucheField = new DatePicker("Date d'embauche");
    private DatePicker dateDebutCtField = new DatePicker("Date de début de contrat");
    private DatePicker dateFinCtField = new DatePicker("Date de fin de contrat");

    private TextField salary = new TextField("Salaire en DH");
    private TextField serviceField = new TextField("Departement");
    private TextField adresseField = new TextField("Adresse");
    private Select<String> situationFamilialeField = new Select();
    private TextField nbEnfantsField = new TextField("Nombre d'enfants");
    private TextField nbrConjeField = new TextField("Nombre de congés");
    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Mot de passe");

    public AddEmployeeForm(AuthService authService, EmployeeService employeeService) {
        this.authService = authService;
        this.employeeService = employeeService;
        addClassName("add-employee");
        H3 title = new H3("Ajouter un employé");
        Button addButton = new Button("Ajouter");
        addButton.addClassName("add-button");
        addButton.addClickListener(buttonClickEvent -> addEmployee());

        genderField.setLabel("Sexe");
        genderField.setItems("Male" , "Femele");

        situationFamilialeField.setLabel("Situation familiale");
        situationFamilialeField.setItems("Celibataire");
        situationFamilialeField.setItems("Marié (e)");
        situationFamilialeField.setItems("Marié (e)",  "Celibataire"  , "Divorcé (e)");

        Hr hr = new Hr();


        HorizontalLayout row1 = new HorizontalLayout(cinField , firstNameField , lastNameField , birthDateField ,genderField);
        HorizontalLayout row2 = new HorizontalLayout(emailField , teleField , adresseField);
        HorizontalLayout row3 = new HorizontalLayout(dateDebutCtField , salary , dateFinCtField , dateEmbaucheField);
        HorizontalLayout row4 = new HorizontalLayout(serviceField , dateEmbaucheField , dateDebutCtField , dateFinCtField);
        HorizontalLayout row5 = new HorizontalLayout(situationFamilialeField , nbEnfantsField);
        HorizontalLayout row6 = new HorizontalLayout(username , password);
        VerticalLayout vr = new VerticalLayout(row1 , hr , row2 , hr , row3 , hr , row4 , hr , row5 , hr , row6);

        add(title, vr , addButton);
    }

    public void addEmployee() {
        if (areFieldsEmpty()) {
            showErrorNotif();
        } else {
            // Create a new Employee object and populate it with the form data
            Employee newEmployee = new Employee();
            newEmployee.setCin(cinField.getValue());
            newEmployee.setFirstName(firstNameField.getValue());
            newEmployee.setLastName(lastNameField.getValue());
            newEmployee.setGender(genderField.getValue());
            newEmployee.setBirthDate(birthDateField.getValue().toString());
            newEmployee.setEmail(emailField.getValue());
            newEmployee.setTele(teleField.getValue());
            newEmployee.setDateEmbauche(dateEmbaucheField.getValue().toString());
            newEmployee.setDateDebutCt(dateDebutCtField.getValue().toString());
            newEmployee.setDateFinCt(dateFinCtField.getValue().toString());
            newEmployee.setDepartement(serviceField.getValue());
            newEmployee.setAdresse(adresseField.getValue());
            newEmployee.setSituation_familiale(situationFamilialeField.getValue());
            newEmployee.setNb_enfants(Integer.parseInt(nbEnfantsField.getValue()));
            newEmployee.setSalary(Double.parseDouble(salary.getValue()));
            newEmployee.setNbr_conje(0);

            try{
                User user = new User();
                user.setUsername(username.getValue());
                user.setPassword(password.getValue());
                authService.addUser(user);

                newEmployee.setUser(user);


                employeeService.addEmployee(newEmployee);
                Notification notification = new Notification("Employé ajouter avec success !");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.TOP_STRETCH);
                notification.setDuration(3000);
                notification.open();
                clearFormFields();
            }catch (Exception exception){
                employeeService.addEmployee(newEmployee);
                Notification notification = new Notification("Une Erreur de serveur est servenu ! ");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.TOP_STRETCH);
                notification.setDuration(3000);
                notification.open();
                clearFormFields();
            }

        }
    }

    private void showErrorNotif() {
        Notification notification = new Notification();
        Icon icon = VaadinIcon.WARNING.create();
        HorizontalLayout layout = new HorizontalLayout(icon , new Text("Veuillez remplir tous les champs ! "));
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.TOP_END);
        notification.setDuration(3000);
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });
        notification.add(layout , closeButton);
        notification.open();
    }

    private boolean areFieldsEmpty() {
        // Check if any of the form fields is empty
        return cinField.isEmpty() || firstNameField.isEmpty() || lastNameField.isEmpty()
                || genderField.isEmpty() || birthDateField.isEmpty() || emailField.isEmpty()
                || teleField.isEmpty() || dateEmbaucheField.isEmpty() || dateDebutCtField.isEmpty()
                || dateFinCtField.isEmpty() || serviceField.isEmpty() || adresseField.isEmpty()
                || situationFamilialeField.isEmpty() || nbEnfantsField.isEmpty() ;
    }

    private void clearFormFields() {
        // Clear all the form fields after adding an employee
        cinField.clear();
        firstNameField.clear();
        lastNameField.clear();
        genderField.clear();
        birthDateField.clear();
        emailField.clear();
        teleField.clear();
        dateEmbaucheField.clear();
        dateDebutCtField.clear();
        dateFinCtField.clear();
        serviceField.clear();
        adresseField.clear();
        situationFamilialeField.clear();
        nbEnfantsField.clear();
        username.clear();
        password.clear();

    }
}
