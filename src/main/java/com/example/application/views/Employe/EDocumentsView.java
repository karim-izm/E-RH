package com.example.application.views.Employe;

import com.example.application.models.Employee;
import com.example.application.models.Prime;
import com.example.application.models.User;
import com.example.application.repositories.EmployeeRepo;
import com.example.application.service.EmployeeService;
import com.example.application.service.PrimeService;
import com.example.application.views.EmployeeLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import java.io.ByteArrayInputStream;
import java.util.List;

@Route(value = "e-documents", layout = EmployeeLayout.class)
@PageTitle("E-Documents")
public class EDocumentsView extends VerticalLayout {
    private Employee currentEmployee;
    private final EmployeeRepo employeeRepo;
    private final EmployeeService employeeService;
    private final PrimeService primeService;
    public EDocumentsView(EmployeeRepo employeeRepo, EmployeeService employeeService, PrimeService primeService) {
        this.employeeRepo = employeeRepo;
        this.employeeService = employeeService;
        this.primeService = primeService;
        add(new H1("E-Documents"));
        User currentUser = (User) VaadinSession.getCurrent().getAttribute("user");
        currentEmployee = employeeRepo.findByUser(currentUser);

        Button employeeInfoButton = new Button("Fiche D'employé");
        employeeInfoButton.addClickListener(e -> showEmployeeInfo());
        add(employeeInfoButton);

        Button payslipHistoryButton = new Button("Bultain de Paie");
        payslipHistoryButton.addClickListener(e -> showPayslipHistory());
        add(payslipHistoryButton);
    }

    private void showEmployeeInfo() {
        Dialog employeeInfoDialog = new Dialog();
        employeeInfoDialog.setModal(true);
        employeeInfoDialog.setWidth("Auto");

        VerticalLayout employeeInfoLayout = new VerticalLayout();
        employeeInfoLayout.setPadding(true);
        employeeInfoLayout.setSpacing(true);
        employeeInfoLayout.setWidth("Auto");

        employeeInfoLayout.add(new H1("Fiche D'employé"));

        employeeInfoLayout.add(createInfoField("Nom Complet:", currentEmployee.getFullName()));
        employeeInfoLayout.add(createInfoField("CIN :", currentEmployee.getCin()));
        employeeInfoLayout.add(createInfoField("Genre :", currentEmployee.getGender()));
        employeeInfoLayout.add(createInfoField("Date de naissance :", currentEmployee.getBirthDate()));
        employeeInfoLayout.add(createInfoField("Email :", currentEmployee.getEmail()));
        employeeInfoLayout.add(createInfoField("Téléphone :", currentEmployee.getTele()));

        employeeInfoLayout.add(createInfoField("Date d'embauche :", currentEmployee.getDateEmbauche()));
        employeeInfoLayout.add(createInfoField("Début du contrat :", currentEmployee.getDateDebutCt()));
        employeeInfoLayout.add(createInfoField("Fin du contrat :", currentEmployee.getDateFinCt()));
        employeeInfoLayout.add(createInfoField("Département :", currentEmployee.getDepartement()));

        employeeInfoLayout.add(createInfoField("Adresse :", currentEmployee.getAdresse()));
        employeeInfoLayout.add(createInfoField("État civil :", currentEmployee.getSituation_familiale()));
        employeeInfoLayout.add(createInfoField("Enfants :", String.valueOf(currentEmployee.getNb_enfants())));

        employeeInfoDialog.add(employeeInfoLayout);
        Button printButton = new Button("Imprimer");
        printButton.addClickListener(event -> {
            byte[] pdfBytes = PdfGenerator.generateEmployeeInfoPDF(currentEmployee);

            if (pdfBytes != null) {
                StreamResource resource = new StreamResource("employee_info.pdf", () -> new ByteArrayInputStream(pdfBytes));
                resource.setContentType("application/pdf");
                resource.setCacheTime(0);

                Anchor anchor = new Anchor(resource.toString());
                anchor.getElement().setAttribute("download", true);
                anchor.add(new Button("Télécharger le PDF"));

                VerticalLayout pdfLayout = new VerticalLayout(anchor);
                pdfLayout.setAlignItems(Alignment.CENTER);

                Dialog pdfDialog = new Dialog();
                pdfDialog.add(pdfLayout);
                pdfDialog.open();
            } else {
                Notification.show("Error PDF");
            }
        });
        employeeInfoDialog.add(printButton);
        employeeInfoDialog.open();
    }


    private Div createInfoField(String label, String value) {
        Div infoField = new Div();
        infoField.setText(label + " " + value);
        infoField.getStyle().set("font-size", "14px");
        infoField.getStyle().set("margin-bottom", "10px");
        return infoField;
    }

    private void showPayslipHistory() {
        // Create a dialog to display payslip history
        Dialog payslipHistoryDialog = new Dialog();
        payslipHistoryDialog.setModal(true);
        payslipHistoryDialog.setWidth("auto");
        payslipHistoryDialog.add(new H1("Bultein de paie"));

        // Create a layout to display the primes
        VerticalLayout primesLayout = new VerticalLayout();
        primesLayout.setPadding(true);
        primesLayout.setSpacing(true);

        // Retrieve and iterate through the employee's primes
        List<Prime> primes = primeService.getPrimesByEmployee(currentEmployee);
        for (Prime prime : primes) {
            // Create a label for each prime and add it to the layout
            String primeInfo = "Date: " + prime.getDate() + ", Montant: " + prime.getMontant() + ", Motif: " + prime.getMotif();
            Paragraph primeLabel = new Paragraph(primeInfo);
            primesLayout.add(primeLabel);
        }

        // Add the primes layout to the dialog
        payslipHistoryDialog.add(primesLayout);

        // Open the dialog
        payslipHistoryDialog.open();
    }

}
