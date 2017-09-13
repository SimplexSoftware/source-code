package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ReportPrevPanel extends Panel {

    @SpringBean
    private ReportDAO reportDAO;

    ReportPrevPanel(String id, final IModel<Meeting> model) {
        super(id, model);

        HibernateModel<Meeting> meeting = new HibernateModel<>(model.getObject());

        SimpleDateFormat enterDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = enterDateFormat.format(model.getObject().getDate());

        add(new Label("meetingDate", date));

        add(new RefreshingView<Report>("pastReports") {
            @Override
            protected Iterator<IModel<Report>> getItemModels() {
                List<Report> reports = reportDAO.findReportsForNewMeeting(meeting.getObject());

                final List<IModel<Report>> listModels = new LinkedList<IModel<Report>>();

                for (Report report : reports) {
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
    }
}