package ru.simplex_software.source_code.web;

import net.sf.wicketautodao.model.HibernateQueryDataProvider;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.simplex_software.source_code.dao.MeetingDAO;
import ru.simplex_software.source_code.dao.ReportDAO;
import ru.simplex_software.source_code.dao.TagDAO;
import ru.simplex_software.source_code.model.Meeting;
import ru.simplex_software.source_code.security.AuthService;

import java.text.ParseException;
import java.util.Date;

public class IndexPage extends SuperPage {
    private static final long serialVersionUID = 1L;

    @SpringBean
    private AuthService authService;
    @SpringBean
    private MeetingDAO meetingDAO;
    @SpringBean
    private ReportDAO reportDAO;
    @SpringBean
    private TagDAO tagDAO;



    public IndexPage() throws ParseException {

        HibernateQueryDataProvider<Meeting, Long> hqDataProviderNewMeetings =
            new HibernateQueryDataProvider(MeetingDAO.class, "findNewMeeting", Model.of(new Date()));

        add(new DataView<Meeting>("newMeeting", hqDataProviderNewMeetings) {
            @Override
            protected void populateItem(Item<Meeting> item) {
                item.add(new ReportNewPanel("reportNewPanel", item.getModel()));
            }
        });

        HibernateQueryDataProvider<Meeting, Long> hqDataProviderPrevMeeting =
            new HibernateQueryDataProvider(MeetingDAO.class, "findPastMeeting", Model.of(new Date()));

        add(new DataView<Meeting>("prevMeeting", hqDataProviderPrevMeeting) {
            @Override
            protected void populateItem(final Item<Meeting> item) {
                item.add(new ReportPrevPanel("reportPrevPanel", item.getModel()));
            }
        });

        add(new WishesPanel("wishes-menu", Model.of("")));
    }

}
