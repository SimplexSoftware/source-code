package ru.simplex_software.source_code.web;

import org.apache.wicket.markup.html.WebPage;

public abstract class SuperPage extends WebPage {
    public SuperPage() {
        add(new MenuPanel("menuPanel"));
    }
}
