package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateModel;
import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.MeetingDAO;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import ru.simplex_software.source_code.model.Speaker;
import ru.simplex_software.source_code.security.AuthService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class IndexPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private AuthService authService;
	@SpringBean
	private MeetingDAO meetingDAO;
	@SpringBean
	private ReportDAO reportDAO;

	public IndexPage(final PageParameters parameters) throws ParseException {
		super(parameters);

		HibernateQueryDataProvider<Meeting, Long> hqDataProviderNewMeetings =
				new	HibernateQueryDataProvider(MeetingDAO.class,"findNewMeeting", Model.of(new Date()));

		DataView<Meeting> dataView = new DataView<Meeting>("newMeeting", hqDataProviderNewMeetings) {
			@Override
			protected void populateItem(Item<Meeting> item) {

				//item.add(new ReportPanel("reportPanel", item.getModel()));
				//
				Report report = item.getModel().getObject().getReports().iterator().next();

				item.add(new MultiLineLabel("report", report.getTitle()));
				item.add(new Label("speaker",  report.getAuthor().getFio()));
				HibernateModel<Report> repModel = new HibernateModel<>(report);


				final Link plusClickLink = new Link<Report>("plusClickLink",repModel)
				{
					@Override
					public void onClick()
					{
						Report report = repModel.getObject();
						Map<Speaker,Boolean> speakMap= report.getWhoLikedIt();
						speakMap.put(authService.getLoginnedAccount(),true);
                        reportDAO.saveOrUpdate(report);
					}
				};
				final Link disClickLink = new Link<Report>("disClickLink",repModel)
				{
					@Override
					public void onClick()
					{
						Report report = repModel.getObject();
						Map<Speaker,Boolean> speakMap= report.getWhoLikedIt();
						speakMap.put(authService.getLoginnedAccount(),false);
                        reportDAO.saveOrUpdate(report);
                    }
				};
				item.add(plusClickLink);
				item.add(disClickLink);

				/**
				 *  Подсчет количества лайков и дизлайков.
				 *  Создаётся стрим значений из Map(whoLikedIt) принадлежащего объекту класса Report.
				 *  Происходит подсчет значений при помощи фильтрации соответствующей условию:
				 *  Для Like - true, для Dislike - false
				 */
				long likes = report.getWhoLikedIt().values().stream().filter(e->e).count();
				long dislikes = report.getWhoLikedIt().values().stream().filter(e->!e).count();

				item.add(new Label("likeCounter", likes));
				item.add(new Label("dislike", dislikes));
			}
		};

		Form<Void> form = new Form<Void>("form"){
			@Override
			protected void onSubmit() {
				Report rep = new Report();
				rep.setTitle(reportTitleModel.getObject());
				rep.setAuthor(authService.getLoginnedAccount());
				rep.setMeeting(meeting.getObject());
				reportDAO.saveOrUpdate(rep);
				meeting.getObject().getReports().add(rep);
				meetingDAO.saveOrUpdate(meeting.getObject());

				reportTitleModel.setObject("");
			}
		};
		form.add(new TextField<>("repInput",reportTitleModel));

		add(form);
		//

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
