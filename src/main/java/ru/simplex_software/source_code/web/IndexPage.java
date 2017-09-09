package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.MeetingDAO;
import ru.simplex_software.source_code.model.Meeting;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IndexPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private MeetingDAO meetingDAO;

	public IndexPage(final PageParameters parameters) throws ParseException {
		super(parameters);

		HibernateQueryDataProvider<Meeting, Long> hqDataProviderNewMeetings =
				new	HibernateQueryDataProvider(MeetingDAO.class,"findNewMeeting", Model.of(new Date()));

		DataView<Meeting> dataView = new DataView<Meeting>("newMeetings", hqDataProviderNewMeetings) {
			@Override
			protected void populateItem(Item<Meeting> item) {
				item.add(new ReportPanel("reportPanel", item.getModel()));
			}
		};

		add(dataView);

		HibernateQueryDataProvider<Meeting, Long> hqDataProviderPrevMeeting =
				new	HibernateQueryDataProvider(MeetingDAO.class,"findPastMeeting", Model.of(new Date()));

		add(new DataView<Meeting>("prevMeeting", hqDataProviderPrevMeeting){
			@Override
			protected void populateItem(final Item<Meeting> item)
			{
				Meeting meet = item.getModelObject();

				item.add(new Label("data", String.valueOf(new SimpleDateFormat
						("dd.MM.yyyy").format(meet.getDate()))));
				item.add(new Label("report", String.valueOf(meet.getReports().get(0).getTitle())));
				item.add(new Label("speaker", String.valueOf("Автор: " + meet.getReports().get(0).getAuthor().getFio())));
		    }
	    });
    }
}
