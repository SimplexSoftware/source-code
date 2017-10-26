package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;

import java.text.SimpleDateFormat;

public class ReportPrevPanel extends Panel {

    @SpringBean
    private ReportDAO reportDAO;



    ReportPrevPanel(String id, final IModel<Meeting> model) {
        super(id, model);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(model.getObject().getDate());
        add(new Label("meeting-date", date));

        HibernateQueryDataProvider<Report, Long> hqDataProviderPrevMeetings
            = new HibernateQueryDataProvider(
                ReportDAO.class, "findReportsForNewMeeting", model);

        add(new DataView<Report>("past-reports", hqDataProviderPrevMeetings) {
            @Override
            protected void populateItem(Item<Report> item) {
                Link<Void> link = new Link<Void>("report") {
                    @Override
                    public void onClick() {
                    }
                };
                link.add(new Label(
                    "report-name", item.getModelObject().getTitle()));
                item.add(link);
                item.add(new Label(
                    "speaker", item.getModelObject().getAuthor().getFio()));
            }
        });
    }
}
