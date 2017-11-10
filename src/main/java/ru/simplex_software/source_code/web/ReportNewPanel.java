package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.VoteUtils;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import ru.simplex_software.source_code.model.Speaker;
import ru.simplex_software.source_code.security.AuthService;

import java.text.SimpleDateFormat;
import java.util.Map;

public class ReportNewPanel extends Panel {

    @SpringBean
    private VoteUtils voteUtils;

    @SpringBean
    private AuthService authService;

    @SpringBean
    private ReportDAO reportDAO;

    private Model<String> reportTitleModel = new Model<>("");

    ReportNewPanel(String id, final IModel<Meeting> model) {
        super(id, model);

        HibernateQueryDataProvider<Report, Long> hqDataProviderNewMeetings
            = new HibernateQueryDataProvider(
                ReportDAO.class, "findReportsForNewMeeting", model);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(model.getObject().getDate());

        add(new Label("meeting-date", date));

        add(new DataView<Report>("future-reports", hqDataProviderNewMeetings) {
            @Override
            protected void populateItem(Item<Report> item) {
                Link<Void> link = new Link<Void>("report") {
                    @Override
                    public void onClick() {
                    }
                };
                link.add(new Label("report-name",
                    item.getModelObject().getTitle()));

                item.add(link);
                item.add(new Label("speaker", item.getModelObject().getAuthor().getFio()));

                item.add(new Link<Void>("like") {
                    @Override
                    public void onClick() {
                        voteUtils.valueReport(item.getModel(), VoteUtils.ValueStrategy.LIKE);
                    }
                });
                item.add(new Link<Void>("dislike") {
                    @Override
                    public void onClick() {
                        voteUtils.valueReport(
                            item.getModel(), VoteUtils.ValueStrategy.DISLIKE);
                    }
                });
                Map<Speaker, Boolean> usersValuedReportMap
                    = item.getModelObject().getWhoLikedIt();

                long likes = usersValuedReportMap
                    .values()
                    .stream()
                    .filter(e -> e)
                    .count();
                long dislikes = usersValuedReportMap
                    .values()
                    .stream()
                    .filter(e -> !e)
                    .count();

                item.add(new Label("like-counter", likes));
                item.add(new Label("dislike-counter", dislikes));
            }
        });

        Form<Void> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                Report rep = new Report();
                rep.setTitle(reportTitleModel.getObject());
                rep.setAuthor(authService.getLogginedUserOrRedirect());
                rep.setMeeting(model.getObject());
                reportDAO.saveOrUpdate(rep);

                reportTitleModel.setObject("");
            }
        };
        form.add(new TextField<>("repInput", reportTitleModel));

        add(form);
    }
}
