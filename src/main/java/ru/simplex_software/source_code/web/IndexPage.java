package ru.simplex_software.source_code.web;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.security.AuthService;

public class IndexPage extends WebPage {
	private static final long serialVersionUID = 1L;
	@SpringBean
	private AuthService authService;

	public IndexPage(final PageParameters parameters) {
		super(parameters);
    }

	@Override
	protected void onRender() {
		super.onRender();
		authService.getLoginnedAccount();
	}
}
