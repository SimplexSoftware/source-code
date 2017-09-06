package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateModelList;
import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.MeetingDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IndexPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private MeetingDAO meetingDAO;

	public IndexPage(final PageParameters parameters)  throws ParseException {
		super(parameters);

		Meeting meeting = meetingDAO.findNewMeeting(new Date());

		SimpleDateFormat enterDateFormat = new SimpleDateFormat("d.MM.yyyy");
		String date = enterDateFormat.format(meeting.getDate());

		add(new Label("meetingDate", date));
		add(new Label("meetingTitle", "Встреча программистов г. Москва"));

		List<Report> reports = meeting.getReports();

		add(new ListView<Report>("newMeeting", new HibernateModelList<Report>(reports)) {
			@Override
			public void populateItem(ListItem<Report> item) {


				Report report = item.getModel().getObject();

				item.add(new MultiLineLabel("title", String.valueOf(report.getTitle())));
				item.add(new Label("speaker", String.valueOf("Автор: " + report.getAuthor().getFio())));
				item.add(new Label("more", "подробнее"));
			}
		});

		add(new CommentForm("commentForm"));

		HibernateQueryDataProvider<Meeting, Long> hqDataProviderPrevMeeting =
				new	HibernateQueryDataProvider(MeetingDAO.class,"findPastMeeting", Model.of(new Date()));

		add(new DataView<Meeting>("prevMeeting", hqDataProviderPrevMeeting){
			@Override
			protected void populateItem(final Item<Meeting> item)
			{
				Meeting meet = item.getModelObject();

				item.add(new Label("data", String.valueOf(new SimpleDateFormat
						("dd.MM.yyyy").format(meet.getDate()))));
				item.add(new Label("title", String.valueOf(meet.getReports().get(0).getTitle())));
				item.add(new Label("speaker", String.valueOf("Автор: " + meet.getReports().get(0).getAuthor().getFio())));

			}
		});
    }


}
