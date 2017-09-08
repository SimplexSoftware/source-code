package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateModel;
import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
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
	private MeetingDAO meetingDAO;
	@SpringBean
	private ReportDAO reportDAO;
    @SpringBean
    private AuthService authService;

    private Model<String> reportTitleModel=new Model<>("");

	public IndexPage(final PageParameters parameters) throws ParseException {
		super(parameters);

		HibernateModel<Meeting> meeting = new HibernateModel<>(meetingDAO.findNewMeeting(new Date()).get(0));

		SimpleDateFormat enterDateFormat = new SimpleDateFormat("d.MM.yyyy");
		String date = enterDateFormat.format(meeting.getObject().getDate());

		add(new Label("meetingDate", date));

		HibernateQueryDataProvider<Meeting, Long> hqDataProviderNewMeeting =
				new	HibernateQueryDataProvider(MeetingDAO.class,"findNewMeeting", Model.of(new Date()));

		DataView<Meeting> dataView = new DataView<Meeting>("newMeeting", hqDataProviderNewMeeting) {
			@Override
			protected void populateItem(Item<Meeting> item) {

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

                        if (!speakMap.containsKey(authService.getLoginnedAccount())) {
                            report.setLikeCounter(report.getLikeCounter() + 1);
                            speakMap.put(authService.getLoginnedAccount(),true);
                            report.setWhoLikedIt(speakMap);
                        } else if (!speakMap.get(authService.getLoginnedAccount())){
                            report.setLikeCounter(report.getLikeCounter() + 1);
                            report.setDislike(report.getDislike() - 1);
                            speakMap.put(authService.getLoginnedAccount(),true);
                            report.setWhoLikedIt(speakMap);
                        }

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

						if (!speakMap.containsKey(authService.getLoginnedAccount())) {
                            report.setDislike(report.getDislike() + 1);
                            speakMap.put(authService.getLoginnedAccount(),false);
                            report.setWhoLikedIt(speakMap);
						} else if (speakMap.get(authService.getLoginnedAccount())){
                            report.setLikeCounter(report.getLikeCounter() - 1);
                            report.setDislike(report.getDislike() + 1);
                            speakMap.put(authService.getLoginnedAccount(),false);
                            report.setWhoLikedIt(speakMap);
                        }
                        reportDAO.saveOrUpdate(report);
                    }
				};
				item.add(plusClickLink);
				item.add(disClickLink);
				item.add(new Label("likeCounter", String.valueOf(repModel.getObject().getLikeCounter())));
				item.add(new Label("dislike", String.valueOf(repModel.getObject().getDislike())));
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
