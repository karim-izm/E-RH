package com.example.application.views.auth;

import com.example.application.models.User;
import com.example.application.views.ADMIN.dashboard.DashboardView;
import com.example.application.views.Employe.DashboardEmployee;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.example.application.service.AuthService;
import com.vaadin.flow.server.VaadinSession;

@Route("login")
@RouteAlias("")
@PageTitle("Login")
public class LoginView extends Div {

    private final AuthService authService;

    public LoginView(AuthService authService) {
        this.authService = authService;
        LoginOverlay loginOverlay = new LoginOverlay();

        loginOverlay.addClassName("custom-login-overlay");
        loginOverlay.setTitle("RADEEJ-Rh");
        loginOverlay.setForgotPasswordButtonVisible(false);
        loginOverlay.setDescription("Application du gestion des ressources humaines");
        add(loginOverlay);
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle("LOGIN");
        i18n.setAdditionalInformation("veuillez contactez admin@societe.com si vous avez des problemes d'authentification.");
        LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setMessage("Username et / ou mot de passe est incorrectes ! Veuillez ressayer");
        i18n.setErrorMessage(errorMessage);
        loginOverlay.getParent().get().getElement().getStyle().set("text-align", "center");

        loginOverlay.setI18n(i18n);
        loginOverlay.setOpened(true);
        loginOverlay.addLoginListener(event -> {
            System.out.println(event.getUsername() + event.getPassword());
            User user = authService.getUserByUsername(event.getUsername());
           if (authService.login(event.getUsername(), event.getPassword()) == 1){
               VaadinSession.getCurrent().setAttribute("user", user);
               UI.getCurrent().navigate(DashboardView.class);
            }

           else if(authService.login(event.getUsername(), event.getPassword()) == 2){
               VaadinSession.getCurrent().setAttribute("user", user);
               UI.getCurrent().navigate(DashboardEmployee.class);

           }
           else if (authService.login(event.getUsername(), event.getPassword()) == -1) {
                loginOverlay.setError(true);
            }
        });
    }

    public void customizeForm(){

    }
}
