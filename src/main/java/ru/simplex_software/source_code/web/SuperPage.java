package ru.simplex_software.source_code.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.security.AuthService;

public abstract class SuperPage extends WebPage {

    @SpringBean
    private AuthService authService;

    public SuperPage() {
        add(new MenuPanel("menuPanel"));
        if (authService.getLoginnedAccount() == null) {
            add(new Fragment("logArea", "login", this));
        } else {
            add(new Fragment("logArea", "logout", this));
        }
    }
}
