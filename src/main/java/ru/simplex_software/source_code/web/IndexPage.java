package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.MeetingDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import ru.simplex_software.source_code.dao.MeetingDAO;
import ru.simplex_software.source_code.model.Meeting;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Date;

public class IndexPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private MeetingDAO meetingDAO;


	public IndexPage(final PageParameters parameters) {
		super(parameters);

		//		List<Report> reports = reportDAO.getReportsForNewMeeting(new Date());
		Meeting meeting = meetingDAO.getReportsForNewMeeting(new Date());

		add(new PropertyListView<Report>("reports", meeting.getReports()) {
			@Override
			public void populateItem(final ListItem<Report> listItem) {
				listItem.add(new MultiLineLabel("title"));
				listItem.add(new Label("speaker"));
				listItem.add(new Label("more"));
			}
		}).setVersioned(false);

		add(new CommentForm("commentForm"));

		HibernateQueryDataProvider<Meeting, Long> hqDataProvider =
				new
				HibernateQueryDataProvider(MeetingDAO.class,"findPastMeeting", Model.of(new Date()));

		add(new DataView<Meeting>("simple", hqDataProvider){
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
