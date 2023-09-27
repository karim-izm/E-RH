package com.example.application.views;

import com.example.application.views.ADMIN.gestion_employes.DeplacementsEmployee;
import com.example.application.views.Employe.DashboardEmployee;
import com.example.application.views.Employe.EDocumentsView;
import com.example.application.views.Employe.RequestCongeView;
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

public class EmployeeLayout extends AppLayout {

    private H2 viewTitle;

    public EmployeeLayout() {
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
        nav.addItem(new SideNavItem("Dashboard", DashboardEmployee.class, LineAwesomeIcon.CHART_AREA_SOLID.create()));


        SideNav conge = new SideNav();
        conge.setLabel("Congés");
        SideNav primes = new SideNav();
        primes.setLabel("Primes");
        SideNav deplacements = new SideNav();
        deplacements.setLabel("Deplacements");
        SideNav docs = new SideNav();
        docs.setLabel("E-Documents");

        docs.addItem(new SideNavItem("E-Documents", EDocumentsView.class , LineAwesomeIcon.HAND_PAPER_SOLID.create()));

        conge.setCollapsible(true);
        conge.addItem(new SideNavItem("Demande de congé" , RequestCongeView.class , LineAwesomeIcon.CALENDAR.create()));

        deplacements.setCollapsible(true);
        deplacements.addItem(new SideNavItem("Deplacements" , DeplacementsEmployee.class , LineAwesomeIcon.PLANE_DEPARTURE_SOLID.create()));


        deplacements.setCollapsible(true);

        VerticalLayout navWrapper = new VerticalLayout(nav  , conge , primes , deplacements , docs);
        navWrapper.setSpacing(true);
        navWrapper.setSizeUndefined();
        return navWrapper;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.setText("RADEEJ-RH copyright 2023");

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
