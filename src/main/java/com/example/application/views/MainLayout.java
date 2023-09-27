package com.example.application.views;

import com.example.application.views.ADMIN.gestion_conges.Demandes_Conges;
import com.example.application.views.ADMIN.gestion_conges.HistoriqueDesConges;
import com.example.application.views.ADMIN.gestion_deplacements.AddDeplacement;
import com.example.application.views.ADMIN.gestion_deplacements.ListDeplacements;
import com.example.application.views.auth.LoginView;
import com.example.application.views.ADMIN.dashboard.DashboardView;
import com.example.application.views.ADMIN.gestion_absences.AbsencesList;
import com.example.application.views.ADMIN.gestion_absences.AddAbsence;
import com.example.application.views.ADMIN.gestion_employes.AddEmployeeForm;
import com.example.application.views.ADMIN.gestion_employes.EmployeesList;
import com.example.application.views.ADMIN.gestion_primes.AddPrime;
import com.example.application.views.ADMIN.gestion_primes.BultainDePaieView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        Button avatarIcon = new Button();
        avatarIcon.setIcon(VaadinIcon.USER.create());
        avatarIcon.addClassName("avatar-button");

        // Create a context menu
        ContextMenu contextMenu = new ContextMenu(avatarIcon);
        MenuItem logoutItem = contextMenu.addItem("Se Deconnecter", e -> {
            UI.getCurrent().navigate(LoginView.class);
        });
        MenuItem editProfile = contextMenu.addItem("Modifier Profile", e -> {
            // Handle logout logic here
        });
        contextMenu.setOpenOnClick(true);
        contextMenu.add(editProfile , logoutItem);

        FlexLayout headerLayout = new FlexLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        headerLayout.add(avatarIcon);

        HorizontalLayout title = new HorizontalLayout(viewTitle);
        title.setWidthFull();

        addToNavbar(true, toggle, title ,  headerLayout);
    }

    private void addDrawerContent() {
        H1 appName = new H1("RADEEJ-RH");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private VerticalLayout createNavigation() {
        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Dashboard", DashboardView.class, LineAwesomeIcon.CHART_AREA_SOLID.create()));

        SideNav employees = new SideNav();
        employees.setLabel("Gestion des employés");
        SideNav conge = new SideNav();
        conge.setLabel("Congés");
        conge.addItem(new SideNavItem("Demandes Des Congés" , Demandes_Conges.class , LineAwesomeIcon.BED_SOLID.create()));
        conge.addItem(new SideNavItem("Historiqes Des Congés" , HistoriqueDesConges.class , LineAwesomeIcon.HISTORY_SOLID.create()));
        SideNav primes = new SideNav();
        primes.setLabel("Primes");
        SideNav deplacements = new SideNav();
        deplacements.setLabel("Deplacements & Dépats");
        deplacements.addItem(new SideNavItem("Ajouter Deplacements" , AddDeplacement.class , LineAwesomeIcon.PLANE_DEPARTURE_SOLID.create()));
        deplacements.addItem(new SideNavItem("List Deplacements" , ListDeplacements.class , LineAwesomeIcon.PLANE_SOLID.create()));
        SideNav absences = new SideNav();
        absences.setLabel("Absences");
        employees.setCollapsible(true);
        conge.setCollapsible(true);
        primes.setCollapsible(true);
        primes.addItem(new SideNavItem("Ajouter Prime" , AddPrime.class , LineAwesomeIcon.MONEY_BILL_WAVE_SOLID.create()));
        primes.addItem(new SideNavItem("Historique des primes" , BultainDePaieView.class , LineAwesomeIcon.LIST_ALT.create()));
        deplacements.setCollapsible(true);
        absences.setCollapsible(true);
        absences.addItem(new SideNavItem("Liste des absences" , AbsencesList.class , LineAwesomeIcon.LIST_SOLID.create()));
        absences.addItem(new SideNavItem("Ajouter absences" , AddAbsence.class , LineAwesomeIcon.XING_SQUARE.create()));
        employees.addItem(new SideNavItem("List des employés", EmployeesList.class, LineAwesomeIcon.USER_CIRCLE_SOLID.create()));
        employees.addItem(new SideNavItem("Ajouter un employé", AddEmployeeForm.class, LineAwesomeIcon.USER_PLUS_SOLID.create()));
        employees.setWidthFull();
        VerticalLayout navWrapper = new VerticalLayout(nav , employees , conge , primes , deplacements , absences);
        navWrapper.setSpacing(true);
        navWrapper.setSizeUndefined();
        return navWrapper;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
