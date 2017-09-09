package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import ru.simplex_software.source_code.security.AuthService;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportPanel extends Panel {

    @SpringBean
    private ReportDAO reportDAO;
    @SpringBean
    private AuthService authService;

    private Model<String> reportTitleModel=new Model<>("");

    ReportPanel(String id, final IModel<Meeting> model) {
        super (id, model);

        HibernateModel<Meeting> meeting = new HibernateModel<>(model.getObject());

        SimpleDateFormat enterDateFormat = new SimpleDateFormat("d.MM.yyyy");
        String date = enterDateFormat.format(model.getObject().getDate());

        add(new Label("meetingDate", date));

        add(new RefreshingView<Report>("newReports") {
            @Override
            protected Iterator<IModel<Report>> getItemModels() {
                List<Report> reports = reportDAO.findReportsForNewMeeting(meeting.getObject());

                final List<IModel<Report>> listModels = new LinkedList<IModel<Report>>();

                for(Report report: reports){
                    listModels.add(Model.of(report));
                }
                return listModels.iterator();
            }

            @Override
            protected void populateItem(Item<Report> item) {
                Report report = item.getModel().getObject();

                item.add(new MultiLineLabel("report", report.getTitle()));
                item.add(new Label("speaker", report.getAuthor().getFio()));
            }
        });

        Form<Void> form = new Form<Void>("form"){
            @Override
            protected void onSubmit() {
                Report rep = new Report();
                rep.setTitle(reportTitleModel.getObject());
                rep.setAuthor(authService.getLoginnedAccount());
                rep.setMeeting(meeting.getObject());
                reportDAO.saveOrUpdate(rep);

                reportTitleModel.setObject("");
            }
        };
        form.add(new TextField<>("repInput",reportTitleModel));

        add(form);
    }
}
