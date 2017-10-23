package ru.simplex_software.source_code.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Application object for your web application.
 */
public class WicketApplication extends WebApplication
	implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return IndexPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();
		getComponentInstantiationListeners().add(
			new SpringComponentInjector(this,
				applicationContext, true));
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");

		mountPage("index.html", IndexPage.class);
		mountPage("about.html", AboutPage.class);
		mountPage("login.html", LoginPage.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {
		WicketApplication.applicationContext = applicationContext;
	}
}
