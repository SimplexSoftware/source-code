package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.model.Report;
import ru.simplex_software.source_code.model.Speaker;
import ru.simplex_software.source_code.security.AuthService;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReportNewPanel extends Panel {

    @SpringBean
    private AuthService authService;
    @SpringBean
    private ReportDAO reportDAO;

    private Model<String> reportTitleModel = new Model<>("");

    ReportNewPanel(String id, final IModel<Meeting> model) {
        super (id, model);

        HibernateModel<Meeting> meeting = new HibernateModel<>(model.getObject());

        SimpleDateFormat enterDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = enterDateFormat.format(model.getObject().getDate());

        add(new Label("meetingDate", date));

        add(new RefreshingView<Report>("newReports") {
            @Override
            protected Iterator<IModel<Report>> getItemModels() {
                List<Report> reports = reportDAO.findReportsForNewMeeting(meeting.getObject());

                final List<IModel<Report>> listModels = new LinkedList<IModel<Report>>();

                for(Report report: reports){
                    listModels.add(new HibernateModel<>(report));
                }
                return listModels.iterator();
            }

            @Override
            protected void populateItem(Item<Report> item) {
                Report report = item.getModel().getObject();

                item.add(new MultiLineLabel("report", report.getTitle()));
                item.add(new Label("speaker", report.getAuthor().getFio()));

                final Link plusClickLink = new Link<Report>("plusClickLink", item.getModel())
                {
                    @Override
                    public void onClick()
                    {
                        Report report = item.getModel().getObject();
                        Map<Speaker,Boolean> speakMap= report.getWhoLikedIt();
                        speakMap.put(authService.getLogginedUserOrRedirect(),true);
                        reportDAO.saveOrUpdate(report);
                    }
                };

                final Link disClickLink = new Link<Report>("disClickLink", item.getModel()){
                    @Override
                    public void onClick()
                    {
                        Report report = item.getModel().getObject();
                        Map<Speaker,Boolean> speakMap= report.getWhoLikedIt();
                        speakMap.put(authService.getLogginedUserOrRedirect(),false);
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
        });

        Form<Void> form = new Form<Void>("form"){
            @Override
            protected void onSubmit() {
                Report rep = new Report();
                rep.setTitle(reportTitleModel.getObject());
                rep.setAuthor(authService.getLogginedUserOrRedirect());
                rep.setMeeting(meeting.getObject());
                reportDAO.saveOrUpdate(rep);

                reportTitleModel.setObject("");
            }
        };
        form.add(new TextField<>("repInput",reportTitleModel));

        add(form);
    }
}
