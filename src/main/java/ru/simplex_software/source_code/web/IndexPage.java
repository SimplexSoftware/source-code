package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateModel;
import net.sf.wicketautodao.model.HibernateModelList;
import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
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
import ru.simplex_software.source_code.security.AuthService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

		Meeting meeting0 = meetingDAO.findNewMeeting(new Date()).get(0);
		SimpleDateFormat enterDateFormat = new SimpleDateFormat("d.MM.yyyy");
		String date = enterDateFormat.format(meeting0.getDate());

		/*HibernateModel<Meeting> meeting = new HibernateModel<>(meetingDAO.findNewMeeting(new Date()));
		HibernateModelList<Report> reports = new HibernateModelList<>(meeting.getObject().getReports());

		SimpleDateFormat enterDateFormat = new SimpleDateFormat("d.MM.yyyy");
		String date = enterDateFormat.format(meeting.getObject().getDate());
*/
		add(new Label("meetingDate", date));

		/*add(new ListView<Report>("newMeeting", reports) {
			@Override
			public void populateItem(ListItem<Report> item) {


				Report report = item.getModel().getObject();

				item.add(new MultiLineLabel("report", String.valueOf(report.getTitle())));
				item.add(new Label("speaker", String.valueOf("Автор: " + report.getAuthor().getFio())));
				item.add(new Label("more", "подробнее"));
			}
		});*/

		add(new RefreshingView<Report>("newMeeting") {
			@Override
			protected void populateItem(Item<Report> item) {

				Report report = item.getModel().getObject();
				item.add(new MultiLineLabel("report", String.valueOf(report.getTitle())));
				item.add(new Label("speaker",  report.getAuthor().getFio()));
			}

			@Override
			protected Iterator<IModel<Report>> getItemModels() {
				Meeting meeting = meetingDAO.findNewMeeting(new Date()).get(0);

				List<Report> reports = meeting.getReports();
				final List<IModel<Report>> reportModel = new ArrayList<IModel<Report>>();

				for(Report report: reports){
					reportModel.add(Model.of(report));
				}

				return reportModel.iterator();
			}
		});

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


        Form<Void> form = new Form<Void>("form"){
            @Override
            protected void onSubmit() {
                Report rep = new Report();
                rep.setTitle(reportTitleModel.getObject());
                rep.setAuthor(authService.getLoginnedAccount());
                reportDAO.saveOrUpdate(rep);
                reports.getObject().add(rep);
				meeting.getObject().setReports(reports.getObject());
				meetingDAO.saveOrUpdate(meeting.getObject());

                reportTitleModel.setObject("");

            }
        };
        form.add(new TextField<>("repInput",reportTitleModel));


        add(form);


    }



}
