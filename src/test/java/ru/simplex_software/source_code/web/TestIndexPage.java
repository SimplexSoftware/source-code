package ru.simplex_software.source_code.web;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Simple test using the WicketTester.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestIndexPage{

	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	@Transactional
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(IndexPage.class, new PageParameters());

		//assert rendered page class
		tester.assertRenderedPage(IndexPage.class);
	}
}
